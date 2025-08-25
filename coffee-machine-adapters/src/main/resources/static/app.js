const $ = sel => document.querySelector(sel);
const $$ = sel => document.querySelectorAll(sel);

let recipes = [];
let polling = null;
let lastStatus = null;

/* ===== helpers ===== */
function pct(current, capacity) {
    if (capacity <= 0) return 0;
    return Math.max(0, Math.min(100, Math.round((current / capacity) * 100)));
}

function ratio(current, capacity) {
    if (capacity <= 0) return 0;
    return Math.max(0, Math.min(1, current / capacity));
}

function setRingProgress(elapsed, total) {
    const ring = $("#brewRing");
    const deg = total > 0 ? Math.round((elapsed / total) * 360) : 0;
    ring.style.background = `conic-gradient(#8ab4f8 ${deg}deg, #334155 ${deg}deg 360deg)`;
}

function setBar(id, current, capacity) {
    const el = $(id);
    if (!el) return;
    el.style.width = pct(current, capacity) + "%";
}

function setNum(id, current, capacity) {
    $(id).textContent = `${current}/${capacity}`;
}

/* ===== CSS Machine driver ===== */
function updateCssMachine(status) {
    const cm = $("#cssMachine");
    if (!cm) return;

    cm.classList.toggle("is-brewing", !!status.brewing && status.poweredOn);
    cm.classList.toggle("is-on", !!status.poweredOn);
    cm.classList.toggle("is-off", !status.poweredOn);

    // Cup fill during brew
    const brewRatio = (status.poweredOn && status.brewing && status.brewTotalSeconds > 0)
        ? Math.min(1, Math.max(0, status.brewElapsedSeconds / status.brewTotalSeconds))
        : 0;
    cm.style.setProperty("--brew-ratio", brewRatio.toFixed(3));

    // Tanks / hopper (waste visual removed)
    const waterR = ratio(status.waterCurrent, status.waterCapacity);
    const beansR = ratio(status.beansCurrent, status.beansCapacity);
    cm.style.setProperty("--water-pct", waterR.toFixed(3));
    cm.style.setProperty("--beans-pct", beansR.toFixed(3));
    $("#waterTank").style.height = (waterR * 100) + "%";
    $("#beansHopper").style.height = (beansR * 100) + "%";

    // Display text
    $("#cmDisplayText").textContent = !status.poweredOn ? "OFF"
        : (status.brewing ? (status.brewRecipeName ?? "Brewing") : "IDLE");

    // Alignments + drip distance limit (cup bottom + 10px from spout top)
    alignCupAndDrip();
}

/* Center the cup under the spout and compute the drip distance to cup bottom + 10px */
function alignCupAndDrip() {
    const cm = $("#cssMachine");
    const cup = $("#cup");
    const spout = $("#spout");
    if (!cm || !cup || !spout) return;

    const spRect = spout.getBoundingClientRect();
    const cupRect = cup.getBoundingClientRect();

    // Horizontal alignment
    const spCenter = spRect.left + spRect.width / 2;
    const cupCenter = cupRect.left + cupRect.width / 2;
    const xOffset = spCenter - cupCenter;
    cm.style.setProperty("--cup-offset", `${xOffset}px`);

    // Vertical distance: from spout top to cup bottom + 10px (limit)
    const dripDistance = Math.max(0, (cupRect.bottom - spRect.top) + 10);
    cm.style.setProperty("--drip-distance", `${dripDistance}px`);
}

/* ===== Recipes rendering + enable/disable ===== */
function renderRecipes() {
    const grid = $("#recipes");
    grid.innerHTML = "";
    recipes.forEach(r => {
        const card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `
      <h3>${r.name}</h3>
      <div class="kv">
        <span>Water</span><div>${r.water} ml</div>
        <span>Beans</span><div>${r.beans} g</div>
        <span>Temp</span><div>${r.temperature} °C</div>
        <span>Grind</span><div>${r.grind}</div>
        <span>Brew</span><div>${r.brewSeconds} s</div>
      </div>
      <button data-id="${r.id}">Brew</button>
    `;
        const btn = card.querySelector("button");
        btn.dataset.water = r.water;
        btn.dataset.beans = r.beans;
        btn.dataset.seconds = r.brewSeconds;
        btn.addEventListener("click", () => startBrew(r.id));
        grid.appendChild(card);
    });

    if (lastStatus) updateRecipeButtons(lastStatus);
}

function updateRecipeButtons(status) {
    $$("#recipes button").forEach(btn => {
        const requiredWater = parseInt(btn.dataset.water, 10) || 0;
        const requiredBeans = parseInt(btn.dataset.beans, 10) || 0;

        let disabled = false;
        let reason = "";

        if (!status.poweredOn) {
            disabled = true;
            reason = "Machine is OFF";
        } else if (status.brewing) {
            disabled = true;
            reason = "Machine is brewing";
        } else if (status.wasteCurrentPucks >= status.wasteCapacityPucks) {
            disabled = true;
            reason = "Waste bin is full";
        } else if (requiredWater > status.waterCurrent) {
            disabled = true;
            reason = "Not enough water";
        } else if (requiredBeans > status.beansCurrent) {
            disabled = true;
            reason = "Not enough beans";
        }

        btn.disabled = disabled;
        btn.title = disabled ? reason : "Brew this recipe";
    });
}

/* ===== Controls enabling ===== */
function updateControls(status) {
    lastStatus = status;

    const on = !!status.poweredOn;
    const brew = !!status.brewing;

    // Progress ring + labels
    if (!on) {
        $("#brewLabel").textContent = "OFF";
        $("#brewTimer").textContent = "--s";
        $("#brewRing").style.background = "conic-gradient(#334155 0deg, #334155 360deg)";
    } else {
        if (brew) {
            $("#brewLabel").textContent = status.brewRecipeName ?? "Brewing";
            $("#brewTimer").textContent = `${status.brewElapsedSeconds}/${status.brewTotalSeconds}s`;
            setRingProgress(status.brewElapsedSeconds, status.brewTotalSeconds);
        } else {
            $("#brewLabel").textContent = "IDLE";
            $("#brewTimer").textContent = "--s";
            setRingProgress(0, 1);
        }
    }

    // Power toggle label
    $("#powerToggleBtn").textContent = on ? "Power Off" : "Power On";
    $("#powerToggleBtn").title = on ? "Turn machine OFF" : "Turn machine ON";

    // Maintenance buttons (toggle always enabled)
    $("#fillWaterBtn").disabled = !on || brew;
    $("#addBeansBtn").disabled = !on || brew;
    $("#emptyWasteBtn").disabled = !on || brew;
    $("#cancelBtn").disabled = !on || !brew;

    // Numeric gauges (waste numeric gauge still shown in dashboard)
    setBar("#waterBar", status.waterCurrent, status.waterCapacity);
    setBar("#beansBar", status.beansCurrent, status.beansCapacity);
    setBar("#wasteBar", status.wasteCurrentPucks, status.wasteCapacityPucks);
    setNum("#waterNum", status.waterCurrent, status.waterCapacity);
    setNum("#beansNum", status.beansCurrent, status.beansCapacity);
    setNum("#wasteNum", status.wasteCurrentPucks, status.wasteCapacityPucks);

    // Recipes
    updateRecipeButtons(status);
}

/* ===== Data fetchers ===== */
async function fetchRecipes() {
    const res = await fetch("/api/recipes");
    recipes = await res.json();
    renderRecipes();
}

async function fetchStatus() {
    const res = await fetch("/api/status");
    const s = await res.json();

    updateControls(s);
    updateCssMachine(s);
}

/* ===== Actions ===== */
async function startBrew(recipeId) {
    const res = await fetch("/api/brew", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({recipeId})
    });
    const data = await res.json();
    if (!data.started) alert(data.message || "Failed to start brew");
}

async function cancelBrew() {
    await fetch("/api/brew/cancel", {method: "POST"});
}

async function refill(type) {
    await fetch("/api/maintenance/refill", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({type})
    });
}

async function powerToggle() {
    const on = !!(lastStatus && lastStatus.poweredOn);
    if (on && lastStatus && lastStatus.brewing) {
        const ok = confirm("Machine is brewing. Stop and power OFF?");
        if (!ok) return;
    }
    await fetch(on ? "/api/power/off" : "/api/power/on", {method: "POST"});
}

/* ===== Collapsible controls ===== */
function restoreControlsCollapsed() {
    const collapsed = localStorage.getItem("controlsCollapsed") === "1";
    const root = $("#controls");
    const toggle = $("#controlsToggle");
    if (collapsed) {
        root.setAttribute("aria-expanded", "false");
        toggle.setAttribute("aria-expanded", "false");
    } else {
        root.setAttribute("aria-expanded", "true");
        toggle.setAttribute("aria-expanded", "true");
    }
}

function toggleControls() {
    const root = $("#controls");
    const expanded = root.getAttribute("aria-expanded") !== "false";
    const newVal = !expanded;
    root.setAttribute("aria-expanded", newVal ? "true" : "false");
    $("#controlsToggle").setAttribute("aria-expanded", newVal ? "true" : "false");
    localStorage.setItem("controlsCollapsed", newVal ? "0" : "1");
}

/* ===== Polling & hooks ===== */
function startPolling() {
    if (polling) clearInterval(polling);
    polling = setInterval(fetchStatus, 1000);
}

function bindControls() {
    $("#controlsToggle").addEventListener("click", toggleControls);
    $("#powerToggleBtn").addEventListener("click", powerToggle);
    $("#fillWaterBtn").addEventListener("click", () => refill("WATER"));
    $("#addBeansBtn").addEventListener("click", () => refill("BEANS"));
    $("#emptyWasteBtn").addEventListener("click", () => refill("WASTE"));
    $("#cancelBtn").addEventListener("click", cancelBrew);
}

window.addEventListener("resize", alignCupAndDrip);

document.addEventListener("DOMContentLoaded", async () => {
    restoreControlsCollapsed();
    bindControls();
    await fetchRecipes();
    await fetchStatus();
    startPolling();
    setTimeout(alignCupAndDrip, 0);
});

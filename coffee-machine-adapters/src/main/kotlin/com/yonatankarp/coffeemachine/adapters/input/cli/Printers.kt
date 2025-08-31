package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Instant

object Printers {
    private val logger = KotlinLogging.logger {}

    fun MachineStatus.printStatus() {
        logger.info { "Machine: $model" }
        logger.info { "  Power: ${if (poweredOn) "ON" else "OFF"}" }
        logger.info { "  Water: ${water.current}/${water.capacity} (${water.remainingRatio()}%)" }
        logger.info { "  Beans: ${beans.current}/${beans.capacity} (${beans.remainingRatio()}%)" }
        logger.info { "  Waste: ${waste.currentPucks}/${waste.capacityPucks} pucks" }
    }

    fun List<Recipe>.printRecipes() {
        if (isEmpty()) {
            logger.error { "No recipes found." }
            return
        }
        logger.info { "Recipes:" }
        forEach {
            logger.info {
                "  - ${it.name.displayName} | water=${it.water} beans=${it.beans} temp=${it.temperature} grind=${it.grind} brew=${it.brewSeconds.second}"
            }
        }
    }

    fun List<DomainEvent>.printEvents() {
        if (isEmpty()) {
            logger.warn { "Nothing happened (maybe machine is OFF or invalid recipe?)." }
            return
        }
        logger.info { "Events:" }
        forEach { e -> logger.info { "  - $e" } }
    }

    fun Brew.printProgress(nowSupplier: () -> Instant = { Instant.now() }) {
        val progress = progress(nowSupplier())
        val percent = (progress.ratio * 100).toInt()
        logger.info {
            val water = "$consumedWater/${recipe.water}"
            val beans = "$consumedBeans/${recipe.beans}"
            val time = "${progress.elapsed.value.format("%.1f")}/${progress.total.value.format("%.1f")}"
            "Progress: $percent% | time=$time | water=$water | beans=$beans | state=$state"
        }
    }
}

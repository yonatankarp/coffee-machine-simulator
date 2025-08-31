package com.yonatankarp.coffeemachine.domain.brew

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import java.time.Instant
import kotlin.math.min

class Brewer(
    private val initialMachine: CoffeeMachine,
    private val initialBrew: Brew,
    private val now: Instant,
    private val autoComplete: Boolean,
) {
    data class Result(
        val machine: CoffeeMachine,
        val brew: Brew,
        val events: List<DomainEvent>,
    )

    fun tick(): Result {
        validatePreconditions()

        val startResult =
            startIfNeededAndConsumeBeansOnce(
                machineBeforeStart = initialMachine,
                brewBeforeStart = initialBrew,
                at = now,
            )

        val waterResult =
            consumeWaterByProgress(
                machineBeforeWater = startResult.machineAfterStart,
                brewBeforeWater = startResult.brewAfterStart,
                at = now,
            )

        val finishResult =
            maybeFinish(
                machineBeforeFinish = waterResult.machineAfterWater,
                brewBeforeFinish = waterResult.brewAfterWater,
                at = now,
                mayFinish = autoComplete,
            )

        val resourcesEvent =
            emitResourcesEvent(
                beansConsumedThisTick = startResult.beansConsumed,
                waterConsumedThisTick = waterResult.waterConsumed,
            )

        val allEvents =
            startResult.events + waterResult.events + finishResult.events + resourcesEvent

        return Result(
            machine = finishResult.machineAfterFinish,
            brew = finishResult.brewAfterFinish,
            events = allEvents,
        )
    }

    // ---------- Steps (pure; do not mutate constructor state) ----------

    private fun validatePreconditions() {
        require(initialBrew.machineId == initialMachine.id) { "Brew belongs to a different machine" }
        require(initialMachine.poweredOn) { "Machine is OFF" }
    }

    /**
     * Transition STARTED -> BREWING and consume beans exactly once.
     * Capacity checks use numeric values on the value objects (no hasAtLeast()).
     */
    private fun startIfNeededAndConsumeBeansOnce(
        machineBeforeStart: CoffeeMachine,
        brewBeforeStart: Brew,
        at: Instant,
    ): StartResult {
        if (brewBeforeStart.state != Brew.State.STARTED) {
            return StartResult(
                machineAfterStart = machineBeforeStart,
                brewAfterStart = brewBeforeStart,
                events = emptyList(),
                beansConsumed = Grams.ZERO,
            )
        }

        val brewAfterStateChange = brewBeforeStart.startBrewing(at)
        val startEvents =
            mutableListOf<DomainEvent>(
                DomainEvent.BrewingStarted(brewAfterStateChange.id, brewAfterStateChange.recipe.name),
            )

        // Consume beans once, on the transition to BREWING
        if (brewAfterStateChange.consumedBeans == Grams.ZERO) {
            val beansRequired = brewAfterStateChange.recipe.beans
            val beansAvailable = machineBeforeStart.beanHopper.current
            check(beansAvailable.value >= beansRequired.value) {
                "Not enough beans: need=$beansRequired, have=$beansAvailable"
            }

            val machineAfterBeans =
                machineBeforeStart.copy(beanHopper = machineBeforeStart.beanHopper.consume(beansRequired))
            val brewAfterBeans = brewAfterStateChange.withAddedBeans(beansRequired)

            return StartResult(
                machineAfterStart = machineAfterBeans,
                brewAfterStart = brewAfterBeans,
                events = startEvents,
                beansConsumed = beansRequired,
            )
        }

        return StartResult(
            machineAfterStart = machineBeforeStart,
            brewAfterStart = brewAfterStateChange,
            events = startEvents,
            beansConsumed = Grams.ZERO,
        )
    }

    private data class StartResult(
        val machineAfterStart: CoffeeMachine,
        val brewAfterStart: Brew,
        val events: List<DomainEvent>,
        val beansConsumed: Grams,
    )

    /**
     * Compute expected *cumulative* water by elapsed time, then apply only the delta for this tick.
     */
    private fun consumeWaterByProgress(
        machineBeforeWater: CoffeeMachine,
        brewBeforeWater: Brew,
        at: Instant,
    ): WaterResult {
        val progress = brewBeforeWater.progress(at)
        val totalRecipeWater = brewBeforeWater.recipe.water.value
        val expectedCumulativeWater = min(totalRecipeWater * progress.ratio, totalRecipeWater)
        val deltaWaterValue = expectedCumulativeWater - brewBeforeWater.consumedWater.value

        if (deltaWaterValue <= 0.0) {
            return WaterResult(
                machineAfterWater = machineBeforeWater,
                brewAfterWater = brewBeforeWater,
                events = emptyList(),
                waterConsumed = Milliliters.ZERO,
            )
        }

        val waterDelta = Milliliters(deltaWaterValue)
        val waterAvailable = machineBeforeWater.waterTank.current
        check(waterAvailable.value >= waterDelta.value) {
            "Not enough water: need=$waterDelta, have=$waterAvailable"
        }

        val machineAfterWater =
            machineBeforeWater.copy(waterTank = machineBeforeWater.waterTank.consume(waterDelta))
        val brewAfterWater = brewBeforeWater.withAddedWater(waterDelta)

        return WaterResult(
            machineAfterWater = machineAfterWater,
            brewAfterWater = brewAfterWater,
            events = emptyList(),
            waterConsumed = waterDelta,
        )
    }

    private data class WaterResult(
        val machineAfterWater: CoffeeMachine,
        val brewAfterWater: Brew,
        val events: List<DomainEvent>,
        val waterConsumed: Milliliters,
    )

    /**
     * If time reached total and auto-complete is enabled, add puck and finish.
     */
    private fun maybeFinish(
        machineBeforeFinish: CoffeeMachine,
        brewBeforeFinish: Brew,
        at: Instant,
        mayFinish: Boolean,
    ): FinishResult {
        if (!mayFinish) return FinishResult(machineBeforeFinish, brewBeforeFinish, emptyList())

        val progress = brewBeforeFinish.progress(at)
        val reachedEnd = progress.elapsed.value >= progress.total.value
        val alreadyFinished = brewBeforeFinish.state == Brew.State.FINISHED

        if (!reachedEnd || alreadyFinished) {
            return FinishResult(machineBeforeFinish, brewBeforeFinish, emptyList())
        }

        val machineAfterPuck = machineBeforeFinish.copy(wasteBin = machineBeforeFinish.wasteBin.addPuck())
        val finishEvents =
            listOf(
                DomainEvent.WastePuckAdded(machineAfterPuck.wasteBin.currentPucks),
                DomainEvent.BrewCompleted(brewBeforeFinish.recipe.name),
            )
        val finishedBrew = brewBeforeFinish.finish(at)

        return FinishResult(machineAfterPuck, finishedBrew, finishEvents)
    }

    private data class FinishResult(
        val machineAfterFinish: CoffeeMachine,
        val brewAfterFinish: Brew,
        val events: List<DomainEvent>,
    )

    /**
     * Emit one ResourcesConsumed event per tick (if anything was consumed).
     */
    private fun emitResourcesEvent(
        beansConsumedThisTick: Grams,
        waterConsumedThisTick: Milliliters,
    ): List<DomainEvent> =
        if (beansConsumedThisTick > Grams.ZERO || waterConsumedThisTick > Milliliters.ZERO) {
            listOf(
                DomainEvent.ResourcesConsumed(
                    water = waterConsumedThisTick,
                    beans = beansConsumedThisTick,
                ),
            )
        } else {
            emptyList()
        }
}

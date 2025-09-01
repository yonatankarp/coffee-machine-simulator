package com.yonatankarp.coffeemachine.domain.brew

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import java.time.Instant
import java.util.UUID

data class Brew(
    val id: Id = Id.new(),
    val machineId: CoffeeMachine.Id,
    val recipe: Recipe,
    val version: Long = 0,
    val state: State = State.STARTED,
    val totalSeconds: BrewSeconds = recipe.brewSeconds,
    val consumedWater: Milliliters = Milliliters.ZERO,
    val consumedBeans: Grams = Grams.ZERO,
    val startedAt: Instant = Instant.now(),
    val finishedAt: Instant? = null,
    val cancelledAt: Instant? = null,
) {
    enum class State { STARTED, BREWING, FINISHED, CANCELLED }

    @JvmInline
    value class Id(
        val value: UUID,
    ) {
        override fun toString(): String = value.toString()

        companion object {
            fun new() = Id(UUID.randomUUID())
        }
    }

    fun progress(now: Instant = Instant.now()): BrewProgress = BrewProgress.of(startedAt, totalSeconds.second, now)

    fun startBrewing(now: Instant = Instant.now()): Brew =
        when (state) {
            State.STARTED -> copy(state = State.BREWING, startedAt = now)
            State.BREWING, State.FINISHED, State.CANCELLED -> this
        }

    fun finish(now: Instant = Instant.now()): Brew = copy(state = State.FINISHED, finishedAt = now)

    fun cancel(now: Instant = Instant.now()): Brew = copy(state = State.CANCELLED, cancelledAt = now)

    fun withAddedWater(delta: Milliliters): Brew = copy(consumedWater = Milliliters(consumedWater.value + delta.value))

    fun withAddedBeans(delta: Grams): Brew = copy(consumedBeans = Grams(consumedBeans.value + delta.value))
}

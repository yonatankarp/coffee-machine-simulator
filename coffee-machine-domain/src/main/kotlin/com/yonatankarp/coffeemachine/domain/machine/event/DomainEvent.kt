package com.yonatankarp.coffeemachine.domain.machine.event

import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import java.time.Instant

sealed interface DomainEvent {
    val occurredAt: Instant

    data class HeatingRequested(
        val target: Celsius,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Heating water to $target"
    }

    data class GrindingRequested(
        val amount: Grams,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Grinding $amount of beans"
    }

    data class BrewingRequested(
        val recipe: Recipe.Name,
        val water: Milliliters,
        val duration: BrewSeconds,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Brewing ${recipe.displayName} with $water of water for ${duration.second}"
    }

    data class ResourcesConsumed(
        val water: Milliliters,
        val beans: Grams,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Consumed $water of water and $beans of beans"
    }

    data class WastePuckAdded(
        val puckCount: Int,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Waste bin now contains $puckCount pucks"
    }

    data class BrewCompleted(
        val recipe: Recipe.Name,
        override val occurredAt: Instant = Instant.now(),
    ) : DomainEvent {
        override fun toString() = "Brew of ${recipe.displayName} completed"
    }
}

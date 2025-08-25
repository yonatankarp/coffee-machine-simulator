package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import io.github.oshai.kotlinlogging.KotlinLogging

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
}

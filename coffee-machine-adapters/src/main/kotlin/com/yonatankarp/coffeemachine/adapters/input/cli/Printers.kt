package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import io.github.oshai.kotlinlogging.KotlinLogging

object Printers {
    private val logger = KotlinLogging.logger {}

    fun MachineStatus.printStatus() {
        logger.info {
            """
            Machine: $model
              Power: ${if (poweredOn) "ON" else "OFF"}
              Water: ${water.current}/${water.capacity} (${water.remainingRatio()}%)
              Beans: ${beans.current}/${beans.capacity} (${beans.remainingRatio()}%)
              Waste: ${waste.currentPucks}/${waste.capacityPucks} pucks
            """.trimIndent()
        }
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

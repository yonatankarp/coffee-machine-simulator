package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printEvents
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printRecipes
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printStatus
import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffeePort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachinePowerPort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineRefillPort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineStatusPort
import com.yonatankarp.coffeemachine.application.ports.input.FindAllRecipesPort
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("cli")
@Component
class CliRunner(
    private val brew: BrewCoffeePort,
    private val power: CoffeeMachinePowerPort,
    private val refill: CoffeeMachineRefillPort,
    private val status: CoffeeMachineStatusPort,
    private val listRecipes: FindAllRecipesPort,
) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun run(vararg args: String?) {
        logger.info { "Coffee Machine CLI ☕️" }
        logger.info { Command.help }

        loop@ while (true) {
            runCatching {
                logger.info { "> " }
                val line = readlnOrNull() ?: break@loop
                val cmd = Command.parse(line)
                if (cmd == null) {
                    logger.info { "Unrecognized command. Type 'help'." }
                    continue@loop
                }
                when (cmd) {
                    is Command.Help -> logger.info { Command.help }
                    is Command.Quit -> break@loop
                    is Command.Power -> power(cmd.on).printStatus()
                    is Command.Status -> status().printStatus()
                    is Command.Recipes -> listRecipes().printRecipes()
                    is Command.RefillWater -> refill(RefillType.WATER).printStatus()
                    is Command.RefillBeans -> refill(RefillType.BEANS).printStatus()
                    is Command.EmptyWaste -> refill(RefillType.WASTE).printStatus()
                    is Command.Brew -> {
                        val recipeName =
                            runCatching { Recipe.Name.from(cmd.recipeName) }
                                .getOrElse {
                                    logger.info { "Unknown recipe '${cmd.recipeName}'. Try 'recipes'." }
                                    null
                                } ?: continue@loop
                        brew(recipeName).printEvents()
                    }
                }
            }.onFailure { logger.error { it.message } }
        }

        logger.info { "Bye 👋" }
    }
}

package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printEvents
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printRecipes
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printStatus
import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffee
import com.yonatankarp.coffeemachine.application.ports.input.BrowseRecipes
import com.yonatankarp.coffeemachine.application.ports.input.GetMachineStatus
import com.yonatankarp.coffeemachine.application.ports.input.ManageMachine
import com.yonatankarp.coffeemachine.application.ports.input.RefillMachine
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
    private val brewCoffee: BrewCoffee,
    private val manageMachine: ManageMachine,
    private val refillMachine: RefillMachine,
    private val getMachineStatus: GetMachineStatus,
    private val browseRecipes: BrowseRecipes,
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
                    is Command.Power -> manageMachine(cmd.on).printStatus()
                    is Command.Status -> getMachineStatus().printStatus()
                    is Command.Recipes -> browseRecipes().printRecipes()
                    is Command.RefillWater -> refillMachine(RefillType.WATER).printStatus()
                    is Command.RefillBeans -> refillMachine(RefillType.BEANS).printStatus()
                    is Command.EmptyWaste -> refillMachine(RefillType.WASTE).printStatus()
                    is Command.Brew -> {
                        val recipeName =
                            runCatching { Recipe.Name.from(cmd.recipeName) }
                                .getOrElse {
                                    logger.info { "Unknown recipe '${cmd.recipeName}'. Try 'recipes'." }
                                    null
                                } ?: continue@loop
                        brewCoffee(recipeName).printEvents()
                    }
                }
            }.onFailure { logger.error { it.message } }
        }

        logger.info { "Bye 👋" }
    }
}

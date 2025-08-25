package com.yonatankarp.coffeemachine.adapters.input.cli

import ch.qos.logback.classic.Level
import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffeePort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachinePowerPort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineRefillPort
import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineStatusPort
import com.yonatankarp.coffeemachine.application.ports.input.FindAllRecipesPort
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatusFixture
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.support.LogCapture
import com.yonatankarp.coffeemachine.support.WithSystemIn
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CliRunnerTest {
    private val brew = mockk<BrewCoffeePort>()
    private val power = mockk<CoffeeMachinePowerPort>()
    private val refill = mockk<CoffeeMachineRefillPort>()
    private val status = mockk<CoffeeMachineStatusPort>()
    private val listRecipes = mockk<FindAllRecipesPort>()

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `prints banner, help and bye for help then quit`() {
        // Given
        every { power(any()) } returns MachineStatusFixture.poweredOn
        every { status.invoke() } returns MachineStatusFixture.poweredOn
        every { refill(any()) } returns MachineStatusFixture.poweredOn
        every { listRecipes.invoke() } returns emptyList()
        every { brew.invoke(any()) } returns emptyList()

        val runner = CliRunner(brew, power, refill, status, listRecipes)

        WithSystemIn("help", "quit").use {
            LogCapture(CliRunner::class.java, Level.INFO).use { logs ->
                // When
                runner.run()

                // Then
                logs.messages.shouldContainAll(
                    "Coffee Machine CLI ☕️",
                    Command.help,
                    "Bye 👋",
                )
                verify(exactly = 0) { brew(any()) }
            }
        }
    }

    @Test
    fun `unknown command logs hint and continues`() {
        // Given
        every { power(any()) } returns MachineStatusFixture.poweredOn
        every { status.invoke() } returns MachineStatusFixture.poweredOn
        every { refill(any()) } returns MachineStatusFixture.poweredOn
        every { listRecipes.invoke() } returns emptyList()
        every { brew.invoke(any()) } returns emptyList()

        val runner = CliRunner(brew, power, refill, status, listRecipes)

        WithSystemIn("does-not-exist", "quit").use {
            LogCapture(CliRunner::class.java).use { logs ->
                // When
                runner.run()

                // Then
                logs.messages.shouldContainAll(
                    "Coffee Machine CLI ☕️",
                    Command.help,
                    "Unrecognized command. Type 'help'.",
                    "Bye 👋",
                )
                verify(exactly = 0) { brew(any()) }
            }
        }
    }

    @Test
    fun `power status refill recipes brew espresso invoke corresponding ports`() {
        // Given
        every { power(true) } returns MachineStatusFixture.poweredOn
        every { status.invoke() } returns MachineStatusFixture.poweredOn
        every { refill(RefillType.WATER) } returns MachineStatusFixture.poweredOn
        every { refill(RefillType.BEANS) } returns MachineStatusFixture.poweredOn
        every { refill(RefillType.WASTE) } returns MachineStatusFixture.poweredOn
        every { listRecipes.invoke() } returns emptyList()
        every { brew(Recipe.Name.ESPRESSO) } returns listOf(DomainEvent.BrewCompleted(Recipe.Name.ESPRESSO))

        val runner = CliRunner(brew, power, refill, status, listRecipes)

        WithSystemIn(
            "power on",
            "status",
            "recipes",
            "refill water",
            "refill beans",
            "refill waste",
            "brew espresso",
            "quit",
        ).use {
            LogCapture(CliRunner::class.java).use { logs ->
                // When
                runner.run()

                // Then
                logs.messages.shouldContainAll(
                    "Coffee Machine CLI ☕️",
                    "Bye 👋",
                )

                verify(exactly = 1) { power(true) }
                verify(exactly = 1) { status.invoke() }
                verify(exactly = 1) { listRecipes.invoke() }
                verify(exactly = 1) { refill(RefillType.WATER) }
                verify(exactly = 1) { refill(RefillType.BEANS) }
                verify(exactly = 1) { refill(RefillType.WASTE) }
                verify(exactly = 1) { brew(Recipe.Name.ESPRESSO) }
            }
        }
    }

    @Test
    fun `brew unknown recipe prints friendly message and does not call brew`() {
        // Given
        every { power(any()) } returns MachineStatusFixture.poweredOn
        every { status.invoke() } returns MachineStatusFixture.poweredOn
        every { refill(any()) } returns MachineStatusFixture.poweredOn
        every { listRecipes.invoke() } returns emptyList()
        every { brew.invoke(any()) } returns emptyList()

        val runner = CliRunner(brew, power, refill, status, listRecipes)

        WithSystemIn("brew not_a_recipe", "quit").use {
            LogCapture(CliRunner::class.java).use { logs ->
                // When
                runner.run()

                logs.messages shouldContain "Unknown recipe 'NOT_A_RECIPE'. Try 'recipes'."
                verify(exactly = 0) { brew(any()) }
            }
        }
    }
}

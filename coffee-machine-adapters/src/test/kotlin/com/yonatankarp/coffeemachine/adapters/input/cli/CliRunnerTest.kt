package com.yonatankarp.coffeemachine.adapters.input.cli

import ch.qos.logback.classic.Level
import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffee
import com.yonatankarp.coffeemachine.application.ports.input.BrowseRecipes
import com.yonatankarp.coffeemachine.application.ports.input.GetMachineStatus
import com.yonatankarp.coffeemachine.application.ports.input.ManageMachine
import com.yonatankarp.coffeemachine.application.ports.input.RefillMachine
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
    private val brewCoffee = mockk<BrewCoffee>()
    private val manageMachine = mockk<ManageMachine>()
    private val refillMachine = mockk<RefillMachine>()
    private val getMachineStatus = mockk<GetMachineStatus>()
    private val browseRecipes = mockk<BrowseRecipes>()

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `prints banner, help and bye for help then quit`() {
        // Given
        every { manageMachine(any()) } returns MachineStatusFixture.poweredOn
        every { getMachineStatus.invoke() } returns MachineStatusFixture.poweredOn
        every { refillMachine(any()) } returns MachineStatusFixture.poweredOn
        every { browseRecipes.invoke() } returns emptyList()
        every { brewCoffee.invoke(any()) } returns emptyList()

        val runner = CliRunner(brewCoffee, manageMachine, refillMachine, getMachineStatus, browseRecipes)

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
                verify(exactly = 0) { brewCoffee(any()) }
            }
        }
    }

    @Test
    fun `unknown command logs hint and continues`() {
        // Given
        every { manageMachine(any()) } returns MachineStatusFixture.poweredOn
        every { getMachineStatus.invoke() } returns MachineStatusFixture.poweredOn
        every { refillMachine(any()) } returns MachineStatusFixture.poweredOn
        every { browseRecipes.invoke() } returns emptyList()
        every { brewCoffee.invoke(any()) } returns emptyList()

        val runner = CliRunner(brewCoffee, manageMachine, refillMachine, getMachineStatus, browseRecipes)

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
                verify(exactly = 0) { brewCoffee(any()) }
            }
        }
    }

    @Test
    fun `power status refill recipes brew espresso invoke corresponding ports`() {
        // Given
        every { manageMachine(true) } returns MachineStatusFixture.poweredOn
        every { getMachineStatus.invoke() } returns MachineStatusFixture.poweredOn
        every { refillMachine(RefillType.WATER) } returns MachineStatusFixture.poweredOn
        every { refillMachine(RefillType.BEANS) } returns MachineStatusFixture.poweredOn
        every { refillMachine(RefillType.WASTE) } returns MachineStatusFixture.poweredOn
        every { browseRecipes.invoke() } returns emptyList()
        every { brewCoffee(Recipe.Name.ESPRESSO) } returns listOf(DomainEvent.BrewCompleted(Recipe.Name.ESPRESSO))

        val runner = CliRunner(brewCoffee, manageMachine, refillMachine, getMachineStatus, browseRecipes)

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

                verify(exactly = 1) { manageMachine(true) }
                verify(exactly = 1) { getMachineStatus.invoke() }
                verify(exactly = 1) { browseRecipes.invoke() }
                verify(exactly = 1) { refillMachine(RefillType.WATER) }
                verify(exactly = 1) { refillMachine(RefillType.BEANS) }
                verify(exactly = 1) { refillMachine(RefillType.WASTE) }
                verify(exactly = 1) { brewCoffee(Recipe.Name.ESPRESSO) }
            }
        }
    }

    @Test
    fun `brew unknown recipe prints friendly message and does not call brew`() {
        // Given
        every { manageMachine(any()) } returns MachineStatusFixture.poweredOn
        every { getMachineStatus.invoke() } returns MachineStatusFixture.poweredOn
        every { refillMachine(any()) } returns MachineStatusFixture.poweredOn
        every { browseRecipes.invoke() } returns emptyList()
        every { brewCoffee.invoke(any()) } returns emptyList()

        val runner = CliRunner(brewCoffee, manageMachine, refillMachine, getMachineStatus, browseRecipes)

        WithSystemIn("brew not_a_recipe", "quit").use {
            LogCapture(CliRunner::class.java).use { logs ->
                // When
                runner.run()

                logs.messages shouldContain "Unknown recipe 'NOT_A_RECIPE'. Try 'recipes'."
                verify(exactly = 0) { brewCoffee(any()) }
            }
        }
    }
}

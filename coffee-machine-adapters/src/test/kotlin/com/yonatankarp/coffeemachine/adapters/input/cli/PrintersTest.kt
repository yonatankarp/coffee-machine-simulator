package com.yonatankarp.coffeemachine.adapters.input.cli

import ch.qos.logback.classic.Level
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printEvents
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printRecipes
import com.yonatankarp.coffeemachine.adapters.input.cli.Printers.printStatus
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatusFixture
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture
import com.yonatankarp.coffeemachine.support.LogCapture
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.OutputCaptureExtension

@ExtendWith(OutputCaptureExtension::class)
class PrintersTest {
    @Test
    fun `should print machine status`() {
        // Given
        val status = MachineStatusFixture.used

        // When
        LogCapture(Printers::class.java, Level.INFO).use { logs ->
            status.printStatus()

            // Then
            logs.messages.shouldContainAll(
                "Machine: KotlinBarista 3000",
                "  Power: ON",
                "  Water: 500ml/1000ml (50%)",
                "  Beans: 300.0g/500.0g (60%)",
                "  Waste: 3/10 pucks",
            )
        }
    }

    @Test
    fun `should print recipes`() {
        // Given
        val recipes =
            listOf(
                RecipeFixture.espresso,
                RecipeFixture.americano,
            )

        // When
        LogCapture(Printers::class.java, Level.INFO).use { logs ->
            recipes.printRecipes()

            // Then
            logs.messages.shouldContainAll(
                "Recipes:",
                "  - Espresso | water=30ml beans=18.0g temp=93.0°C grind=FINE brew=25.0s",
                "  - Americano | water=120ml beans=18.0g temp=93.0°C grind=MEDIUM brew=25.0s",
            )
        }
    }

    @Test
    fun `should print 'no recipes' message`() {
        // Given
        val recipes = emptyList<Recipe>()

        // When
        LogCapture(Printers::class.java, Level.INFO).use { logs ->
            recipes.printRecipes()

            // Then
            logs.messages.shouldContainAll(
                "No recipes found.",
            )
        }
    }

    @Test
    fun `should print events`() {
        // Given
        val events =
            listOf(
                DomainEvent.BrewCompleted(Recipe.Name.ESPRESSO),
            )

        // When
        LogCapture(Printers::class.java, Level.INFO).use { logs ->
            events.printEvents()

            // Then
            logs.messages.shouldContainAll(
                "Events:",
                "  - Brew of Espresso completed",
            )
        }
    }

    @Test
    fun `should print 'nothing happened' message`() {
        // Given
        val events = emptyList<DomainEvent>()

        // When
        LogCapture(Printers::class.java, Level.INFO).use { logs ->
            events.printEvents()

            // Then
            logs.messages.size shouldBe 1
            logs.messages.shouldContainAll(
                "Nothing happened (maybe machine is OFF or invalid recipe?).",
            )
        }
    }
}

package com.yonatankarp.coffeemachine.adapters.output.cli

import ch.qos.logback.classic.Level
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.support.LogCapture
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import org.junit.jupiter.api.Test
import kotlin.use

class ConsoleDomainEventPublisherTest {
    @Test
    fun `should publish single event`() {
        // Given
        val publisher = ConsoleDomainEventPublisher()

        // When
        LogCapture(ConsoleDomainEventPublisher::class.java, Level.INFO).use { logs ->
            publisher.publish(DomainEvent.BrewCompleted(Recipe.Name.ESPRESSO))

            // Then
            logs.messages shouldContain "[event] Brew of Espresso completed"
        }
    }

    @Test
    fun `should publish multiple events`() {
        // Given
        val publisher = ConsoleDomainEventPublisher()

        // When
        LogCapture(ConsoleDomainEventPublisher::class.java, Level.INFO).use { logs ->
            publisher.publishAll(
                listOf(
                    DomainEvent.BrewCompleted(Recipe.Name.ESPRESSO),
                    DomainEvent.BrewCompleted(Recipe.Name.AMERICANO),
                ),
            )

            // Then
            logs.messages.shouldContainAll(
                "[event] Brew of Espresso completed",
                "[event] Brew of Americano completed",
            )
        }
    }
}

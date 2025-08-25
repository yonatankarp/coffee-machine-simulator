package com.yonatankarp.coffeemachine.adapters.output.cli

import com.yonatankarp.coffeemachine.application.ports.output.EventPublisher
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ConsoleDomainEventPublisher : EventPublisher {
    private val logger = KotlinLogging.logger {}

    override fun publish(event: DomainEvent) {
        logger.info { "[event] $event" }
    }

    override fun publishAll(events: List<DomainEvent>) {
        events.forEach { publish(it) }
    }
}

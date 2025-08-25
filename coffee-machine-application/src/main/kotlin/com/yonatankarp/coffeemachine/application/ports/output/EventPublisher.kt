package com.yonatankarp.coffeemachine.application.ports.output

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)

    fun publishAll(events: List<DomainEvent>) = events.forEach(::publish)
}

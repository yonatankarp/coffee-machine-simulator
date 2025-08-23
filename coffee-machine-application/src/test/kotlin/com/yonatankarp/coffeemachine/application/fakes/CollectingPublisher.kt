package com.yonatankarp.coffeemachine.application.fakes

import com.yonatankarp.coffeemachine.application.ports.output.DomainEventPublisher
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent

class CollectingPublisher : DomainEventPublisher {
    val published = mutableListOf<DomainEvent>()

    override fun publish(event: DomainEvent) {
        published += event
    }
}

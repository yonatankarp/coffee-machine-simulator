package com.yonatankarp.coffeemachine.application.fakes

import com.yonatankarp.coffeemachine.application.ports.output.EventPublisher
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent

class CollectingPublisher : EventPublisher {
    val published = mutableListOf<DomainEvent>()

    override fun publish(event: DomainEvent) {
        published += event
    }
}

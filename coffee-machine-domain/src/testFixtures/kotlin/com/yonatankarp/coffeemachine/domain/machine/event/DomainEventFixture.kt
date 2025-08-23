package com.yonatankarp.coffeemachine.domain.machine.event

import kotlin.reflect.KClass

object DomainEventFixture {
    val eventClasses: List<KClass<out DomainEvent>> =
        listOf(
            DomainEvent.HeatingRequested::class,
            DomainEvent.GrindingRequested::class,
            DomainEvent.BrewingRequested::class,
            DomainEvent.ResourcesConsumed::class,
            DomainEvent.WastePuckAdded::class,
            DomainEvent.BrewCompleted::class,
        )
}

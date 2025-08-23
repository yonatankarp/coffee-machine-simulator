package com.yonatankarp.coffeemachine.domain.machine.event

object DomainEventFixture {
    val eventClasses =
        listOf(
            DomainEvent.HeatingRequested::class,
            DomainEvent.GrindingRequested::class,
            DomainEvent.BrewingRequested::class,
            DomainEvent.ResourcesConsumed::class,
            DomainEvent.WastePuckAdded::class,
            DomainEvent.BrewCompleted::class,
        )
}

package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.AdvanceBrew
import com.yonatankarp.coffeemachine.application.ports.output.EventPublisher
import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.brew.port.BrewRepository
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import java.time.Instant

class AdvanceBrewUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
    private val brewRepository: BrewRepository,
    private val publisher: EventPublisher,
) : AdvanceBrew {
    override fun invoke(brewId: Brew.Id): Brew {
        val brew = brewRepository.findById(brewId) ?: throw IllegalArgumentException("Unknown brew $brewId")
        val machine = coffeeMachineRepository.load()

        val outcome = machine.brew(brew, now = Instant.now(), autoComplete = true)

        coffeeMachineRepository.save(outcome.updatedMachine)
        brewRepository.save(outcome.updatedBrew)
        publisher.publishAll(outcome.events)

        return outcome.updatedBrew
    }
}

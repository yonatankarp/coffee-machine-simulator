package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffeePort
import com.yonatankarp.coffeemachine.application.ports.output.DomainEventPublisher
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.port.MachineStateRepository
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository

class BrewCoffeeUseCase(
    private val stateRepository: MachineStateRepository,
    private val recipeRepository: RecipeRepository,
    private val publisher: DomainEventPublisher,
) : BrewCoffeePort {
    override fun invoke(recipeName: Recipe.Name): List<DomainEvent> {
        val recipe = recipeRepository.findByName(recipeName) ?: return emptyList()

        val machine = stateRepository.load()
        val outcome = machine.brew(recipe)
        stateRepository.save(outcome.updatedMachine)

        return outcome.events
            .also { publisher.publishAll(outcome.events) }
    }
}

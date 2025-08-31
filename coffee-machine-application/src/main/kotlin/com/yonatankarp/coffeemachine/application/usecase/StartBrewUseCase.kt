package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.StartBrew
import com.yonatankarp.coffeemachine.application.ports.output.EventPublisher
import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.brew.port.BrewRepository
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository
import java.time.Instant

class StartBrewUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
    private val recipeRepository: RecipeRepository,
    private val brewRepository: BrewRepository,
    private val publisher: EventPublisher,
) : StartBrew {
    override fun invoke(recipeName: Recipe.Name): Brew {
        val recipe = recipeRepository.findByName(recipeName) ?: throw IllegalArgumentException("Unknown recipe $recipeName")
        val machine = coffeeMachineRepository.load()

        val brew = Brew(machineId = machine.id, recipe = recipe, startedAt = Instant.now())
        val outcome = machine.advance(brew, now = Instant.now(), autoComplete = false)

        coffeeMachineRepository.save(outcome.updatedMachine)
        brewRepository.save(outcome.updatedBrew)
        publisher.publishAll(outcome.events)
        return outcome.updatedBrew
    }
}

package com.yonatankarp.coffeemachine.adapters

import com.yonatankarp.coffeemachine.application.ports.output.EventPublisher
import com.yonatankarp.coffeemachine.application.usecase.AdvanceBrewUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachinePowerUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachineRefillUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachineStatusUseCase
import com.yonatankarp.coffeemachine.application.usecase.FindAllRecipesUseCase
import com.yonatankarp.coffeemachine.application.usecase.StartBrewUseCase
import com.yonatankarp.coffeemachine.domain.brew.port.BrewRepository
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdaptersConfiguration {
    @Bean
    fun startBrewUseCase(
        coffeeMachineRepository: CoffeeMachineRepository,
        recipeRepository: RecipeRepository,
        brewRepository: BrewRepository,
        publisher: EventPublisher,
    ) = StartBrewUseCase(
        coffeeMachineRepository,
        recipeRepository,
        brewRepository,
        publisher,
    )

    @Bean
    fun advanceBrewUseCase(
        coffeeMachineRepository: CoffeeMachineRepository,
        brewRepository: BrewRepository,
        publisher: EventPublisher,
    ) = AdvanceBrewUseCase(coffeeMachineRepository, brewRepository, publisher)

    @Bean
    fun coffeeMachinePowerUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachinePowerUseCase(coffeeMachineRepository)

    @Bean
    fun coffeeMachineRefillUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachineRefillUseCase(coffeeMachineRepository)

    @Bean
    fun coffeeMachineStatusUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachineStatusUseCase(coffeeMachineRepository)

    @Bean
    fun findAllRecipesUseCase(recipeRepository: RecipeRepository) = FindAllRecipesUseCase(recipeRepository)
}

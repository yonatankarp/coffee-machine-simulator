package com.yonatankarp.coffeemachine.adapters

import com.yonatankarp.coffeemachine.application.ports.output.DomainEventPublisher
import com.yonatankarp.coffeemachine.application.usecase.BrewCoffeeUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachinePowerUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachineRefillUseCase
import com.yonatankarp.coffeemachine.application.usecase.CoffeeMachineStatusUseCase
import com.yonatankarp.coffeemachine.application.usecase.FindAllRecipesUseCase
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdaptersConfiguration {
    @Bean
    fun brewCoffeeUseCase(
        coffeeMachineRepository: CoffeeMachineRepository,
        recipeRepository: RecipeRepository,
        publisher: DomainEventPublisher,
    ) = BrewCoffeeUseCase(coffeeMachineRepository, recipeRepository, publisher)

    @Bean
    fun coffeeMachinePowerUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachinePowerUseCase(coffeeMachineRepository)

    @Bean
    fun coffeeMachineRefillUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachineRefillUseCase(coffeeMachineRepository)

    @Bean
    fun coffeeMachineStatusUseCase(coffeeMachineRepository: CoffeeMachineRepository) = CoffeeMachineStatusUseCase(coffeeMachineRepository)

    @Bean
    fun findAllRecipesUseCase(recipeRepository: RecipeRepository) = FindAllRecipesUseCase(recipeRepository)
}

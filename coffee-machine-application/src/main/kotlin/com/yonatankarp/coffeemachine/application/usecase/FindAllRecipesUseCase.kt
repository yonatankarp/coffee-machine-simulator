package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.FindAllRecipesPort
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository

class FindAllRecipesUseCase(
    private val recipeRepository: RecipeRepository,
) : FindAllRecipesPort {
    override fun invoke(): List<Recipe> = recipeRepository.list()
}

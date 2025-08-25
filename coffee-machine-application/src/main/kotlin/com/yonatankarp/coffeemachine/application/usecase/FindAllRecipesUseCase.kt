package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.BrowseRecipes
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository

class FindAllRecipesUseCase(
    private val recipeRepository: RecipeRepository,
) : BrowseRecipes {
    override fun invoke(): List<Recipe> = recipeRepository.list()
}

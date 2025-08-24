package com.yonatankarp.coffeemachine.application.fakes

import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository

class FakeRecipeRepository(
    recipes: List<Recipe>,
) : RecipeRepository {
    constructor(vararg recipes: Recipe) : this(recipes.toList())

    private val byName = recipes.associateBy { it.name }.toMutableMap()

    override fun save(recipe: Recipe): Recipe {
        byName[recipe.name] = recipe
        return recipe
    }

    override fun findByName(name: Recipe.Name): Recipe? = byName[name]

    override fun list(): List<Recipe> = byName.values.toList()
}

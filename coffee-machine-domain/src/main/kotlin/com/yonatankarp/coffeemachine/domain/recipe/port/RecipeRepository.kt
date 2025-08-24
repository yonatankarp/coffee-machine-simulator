package com.yonatankarp.coffeemachine.domain.recipe.port

import com.yonatankarp.coffeemachine.domain.recipe.Recipe

interface RecipeRepository {
    fun save(recipe: Recipe): Recipe

    fun findByName(name: Recipe.Name): Recipe?

    fun list(): List<Recipe>
}

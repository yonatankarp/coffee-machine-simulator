package com.yonatankarp.coffeemachine.domain.recipe.port

import com.yonatankarp.coffeemachine.domain.recipe.Recipe

interface RecipeRepository {
    fun findByName(name: String): Recipe?

    fun list(): List<Recipe>
}

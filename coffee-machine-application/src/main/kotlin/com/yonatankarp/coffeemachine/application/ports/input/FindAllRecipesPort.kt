package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.recipe.Recipe

fun interface FindAllRecipesPort {
    operator fun invoke(): List<Recipe>
}

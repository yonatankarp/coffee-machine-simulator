package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.recipe.Recipe

fun interface StartBrew {
    operator fun invoke(recipeName: Recipe.Name): Brew
}

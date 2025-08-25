package com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper

import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.openapi.model.Recipe as RecipeApi

object RecipeMapper {
    fun Recipe.toApi() =
        RecipeApi(
            id = this.id.value,
            name = this.name.displayName,
            water = this.water.value.toInt(),
            beans = this.beans.value.toInt(),
            temperature = this.temperature.value.toInt(),
            grind = this.grind.name,
            brewSeconds =
                this.brewSeconds.second.value
                    .toInt(),
        )

    fun Iterable<Recipe>.toApi() = map { it.toApi() }
}

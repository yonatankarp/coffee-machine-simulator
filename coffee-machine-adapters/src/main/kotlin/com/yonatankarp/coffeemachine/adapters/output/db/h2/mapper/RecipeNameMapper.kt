package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeNameEntity
import com.yonatankarp.coffeemachine.domain.recipe.Recipe

object RecipeNameMapper {
    fun RecipeNameEntity.toDomain() =
        when (this) {
            RecipeNameEntity.AMERICANO -> Recipe.Name.AMERICANO
            RecipeNameEntity.CAPPUCCINO -> Recipe.Name.CAPPUCCINO
            RecipeNameEntity.CORTADO -> Recipe.Name.CORTADO
            RecipeNameEntity.DOUBLE_ESPRESSO -> Recipe.Name.DOUBLE_ESPRESSO
            RecipeNameEntity.ESPRESSO -> Recipe.Name.ESPRESSO
            RecipeNameEntity.FLAT_WHITE -> Recipe.Name.FLAT_WHITE
            RecipeNameEntity.LATTE -> Recipe.Name.LATTE
            RecipeNameEntity.LONG_BLACK -> Recipe.Name.LONG_BLACK
            RecipeNameEntity.LUNGO -> Recipe.Name.LUNGO
            RecipeNameEntity.RISTRETTO -> Recipe.Name.RISTRETTO
        }

    fun Recipe.Name.toEntity() =
        when (this) {
            Recipe.Name.AMERICANO -> RecipeNameEntity.AMERICANO
            Recipe.Name.CAPPUCCINO -> RecipeNameEntity.CAPPUCCINO
            Recipe.Name.CORTADO -> RecipeNameEntity.CORTADO
            Recipe.Name.DOUBLE_ESPRESSO -> RecipeNameEntity.DOUBLE_ESPRESSO
            Recipe.Name.ESPRESSO -> RecipeNameEntity.ESPRESSO
            Recipe.Name.FLAT_WHITE -> RecipeNameEntity.FLAT_WHITE
            Recipe.Name.LATTE -> RecipeNameEntity.LATTE
            Recipe.Name.LONG_BLACK -> RecipeNameEntity.LONG_BLACK
            Recipe.Name.LUNGO -> RecipeNameEntity.LUNGO
            Recipe.Name.RISTRETTO -> RecipeNameEntity.RISTRETTO
        }
}

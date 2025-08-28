package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.GrindSizeMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.GrindSizeMapper.toEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toEntity
import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds

object RecipeMapper {
    fun RecipeEntity.toDomain() =
        Recipe(
            id = Recipe.Id(id),
            name = name.toDomain(),
            water = Milliliters(water),
            beans = Grams(beans),
            temperature = Celsius(temperature),
            grind = grind.toDomain(),
            brewSeconds = BrewSeconds(Seconds(brewSeconds)),
        )

    fun Iterable<RecipeEntity>.toDomain(): List<Recipe> = map { it.toDomain() }

    fun Recipe.toEntity() =
        RecipeEntity(
            id = id.value,
            name = name.toEntity(),
            water = water.value,
            beans = beans.value,
            temperature = temperature.value,
            grind = grind.toEntity(),
            brewSeconds = brewSeconds.second.value,
        )

    fun Iterable<Recipe>.toEntity(): List<RecipeEntity> = map { it.toEntity() }
}

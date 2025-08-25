package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.GrindSizeMapper.toEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toEntity
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RecipeMapperTest {
    @Test
    fun `should map from entity to domain`() {
        // Given
        val recipe = RecipeFixture.espresso
        val entity =
            RecipeEntity(
                id = recipe.id.value,
                name = recipe.name.toEntity(),
                water = recipe.water.value,
                beans = recipe.beans.value,
                temperature = recipe.temperature.value,
                grind = recipe.grind.toEntity(),
                brewSeconds = recipe.brewSeconds.second.value,
            )

        // When
        val domain = entity.toDomain()

        // Then
        domain shouldBe recipe
    }

    @Test
    fun `should map from domain to entity`() {
        // Given
        val domain = RecipeFixture.espresso

        // When
        val entity = domain.toEntity()

        // Then
        entity.id shouldBe domain.id.value
        entity.name shouldBe domain.name.toEntity()
        entity.water shouldBe domain.water.value
        entity.beans shouldBe domain.beans.value
        entity.temperature shouldBe domain.temperature.value
        entity.grind shouldBe domain.grind.toEntity()
        entity.brewSeconds shouldBe domain.brewSeconds.second.value
    }
}

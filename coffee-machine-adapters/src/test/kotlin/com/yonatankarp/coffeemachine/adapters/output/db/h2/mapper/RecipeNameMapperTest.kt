package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeNameEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toEntity
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RecipeNameMapperTest {
    @Test
    fun `should map from entity to domain`() {
        RecipeNameEntity.entries.forEach { entity ->
            val domain = entity.toDomain()
            domain.name shouldBe entity.name
        }
    }

    @Test
    fun `should map from domain to entity`() {
        Recipe.Name.entries.forEach { domain ->
            val entity = domain.toEntity()
            entity.name shouldBe domain.name
        }
    }
}

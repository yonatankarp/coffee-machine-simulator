package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeNameMapper.toEntity
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.port.RecipeRepository
import org.springframework.stereotype.Repository

@Repository
class RecipeH2Repository(
    private val jpaRepository: RecipeH2JpaRepository,
) : RecipeRepository {
    override fun save(recipe: Recipe): Recipe = jpaRepository.save(recipe.toEntity()).toDomain()

    override fun findByName(name: Recipe.Name): Recipe? = jpaRepository.findByName(name.toEntity())?.toDomain()

    override fun list(): List<Recipe> = jpaRepository.findAll().toDomain()
}

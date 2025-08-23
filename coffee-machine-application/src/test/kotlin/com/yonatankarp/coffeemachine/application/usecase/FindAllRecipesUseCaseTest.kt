package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.fakes.FakeRecipeRepository
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture.americano
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture.espresso
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FindAllRecipesUseCaseTest {
    @Test
    fun `returns all recipes from repository`() {
        // Given
        val recipeRepository = FakeRecipeRepository(espresso, americano)
        val findAllRecipes = FindAllRecipesUseCase(recipeRepository)

        // When
        val result = findAllRecipes()

        // Then
        result.size shouldBe 2
        result.shouldContainExactly(listOf(espresso, americano))
    }

    @Test
    fun `returns empty list when repository has no recipes`() {
        // Given
        val recipeRepository = FakeRecipeRepository()
        val findAllRecipes = FindAllRecipesUseCase(recipeRepository)

        // When
        val result = findAllRecipes()

        // Then
        result.size.shouldBeZero()
        result shouldBe emptyList()
    }
}

package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest(showSql = true)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RecipeH2RepositoryTest(
    private val jpaRepository: RecipeH2JpaRepository,
) {
    @Test
    fun `should load recipe`() {
        // Given
        val recipe = RecipeFixture.espresso
        val repository = RecipeH2Repository(jpaRepository)

        // When
        val loadedRecipe = repository.findByName(Recipe.Name.ESPRESSO)

        // Then
        loadedRecipe shouldBe recipe
    }
}

package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        "spring.main.web-application-type=none",
        "spring.flyway.enabled=true",
        "spring.jpa.hibernate.ddl-auto=none",
    ],
)
@Transactional
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

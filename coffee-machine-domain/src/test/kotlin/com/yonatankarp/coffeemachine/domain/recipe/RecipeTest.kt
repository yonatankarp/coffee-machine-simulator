package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.recipe.Recipe.Name
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RecipeTest {
    @Test
    fun `recipe name cannot be blank`() {
        // Given
        val blank = "  "

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                Name(blank)
            }

        // Then
        ex.message shouldBe "Recipe name cannot be blank"
    }

    @Test
    fun `recipe name toString returns the raw value`() {
        // Given
        val name = Name("Americano")

        // When
        val text = name.toString()

        // Then
        text shouldBe "Americano"
    }
}

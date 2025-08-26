package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.recipe.exceptions.RecipeException
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RecipeTest {
    @Nested
    inner class Name {
        @ParameterizedTest(name = "from(\"{0}\") -> {1}")
        @CsvSource(
            "ESPRESSO, ESPRESSO",
            " espresso , ESPRESSO",
            "eSpReSsO, ESPRESSO",
            "Espresso, ESPRESSO",
            "  Double   Espresso , DOUBLE_ESPRESSO",
            "double-espresso, DOUBLE_ESPRESSO",
            "DOUBLE_ESPRESSO, DOUBLE_ESPRESSO",
            "long  black, LONG_BLACK",
            "LONG-BLACK, LONG_BLACK",
            "LONG_BLACK, LONG_BLACK",
            "flat_white, FLAT_WHITE",
            "flat-  white, FLAT_WHITE",
            "  flat   white  , FLAT_WHITE",
            "Americano, AMERICANO",
            "cappuccino, CAPPUCCINO",
            "cortado, CORTADO",
            "ristretto, RISTRETTO",
            "lungo, LUNGO",
            "latte, LATTE",
        )
        fun `parses common variants of name or displayName`(
            input: String,
            expectedEnumName: String,
        ) {
            // Given
            // When
            val actual = Recipe.Name.from(input)

            // Then
            actual.name shouldBe expectedEnumName
        }
    }

    @ParameterizedTest(name = "DisplayName mapping for {0}")
    @MethodSource("allDisplayNameCases")
    fun `accepts each enum displayName as input`(
        displayName: String,
        expected: Recipe.Name,
    ) {
        // Given
        // When
        val actual = Recipe.Name.from(displayName)

        // Then
        actual shouldBe expected
    }

    @Test
    fun `normalization rules are applied consistently`() {
        // Given
        val inputs =
            listOf(
                "  DOUBLE   ESPRESSO ",
                "double-espresso",
                "DOUBLE_ESPRESSO",
                "Double   Espresso",
                "dOuBlE   eSpReSsO",
            )

        // When
        val results = inputs.map { Recipe.Name.from(it) }

        // Then
        results.distinct() shouldContainExactlyInAnyOrder listOf(Recipe.Name.DOUBLE_ESPRESSO)
    }

    @Test
    fun `throws with helpful message on unknown recipe`() {
        // Given
        val bad = "macchiatissimo"

        // When / Then
        shouldThrowWithMessage<RecipeException.UnknownRecipeName>(
            "Unknown recipe: macchiatissimo. Expected one of: Espresso, Ristretto, Lungo, Americano, Long Black, Latte, Cappuccino, Flat White, Double Espresso, Cortado",
        ) {
            Recipe.Name.from(bad)
        }
    }

    companion object {
        @JvmStatic
        fun allDisplayNameCases(): Stream<Arguments> =
            Recipe.Name.entries
                .stream()
                .map { Arguments.of(it.displayName, it) }
    }
}

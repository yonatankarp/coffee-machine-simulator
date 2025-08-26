package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.recipe.exceptions.RecipeException
import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

data class Recipe(
    val id: RecipeId,
    val name: Name,
    val water: Milliliters,
    val beans: Grams,
    val temperature: Celsius,
    val grind: GrindSize,
    val brewSeconds: BrewSeconds,
) {
    enum class GrindSize { FINE, MEDIUM, COARSE }

    enum class Name(
        val displayName: String,
    ) {
        ESPRESSO("Espresso"),
        RISTRETTO("Ristretto"),
        LUNGO("Lungo"),
        AMERICANO("Americano"),
        LONG_BLACK("Long Black"),
        LATTE("Latte"),
        CAPPUCCINO("Cappuccino"),
        FLAT_WHITE("Flat White"),
        DOUBLE_ESPRESSO("Double Espresso"),
        CORTADO("Cortado"),
        ;

        companion object {
            private fun normalize(input: String): String =
                input
                    .trim()
                    .replace('-', ' ')
                    .replace('_', ' ')
                    .replace("\\s+".toRegex(), " ")
                    .uppercase()

            fun from(value: String): Name {
                val normalized = normalize(value)
                return entries.firstOrNull { enum ->
                    enum.name == normalized.replace(' ', '_') ||
                        enum.displayName.uppercase() == normalized
                } ?: throw RecipeException.UnknownRecipeName(value)
            }
        }
    }
}

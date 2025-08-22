package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

data class Recipe(
    val name: Name,
    val water: Milliliters,
    val beans: Grams,
    val temperature: Celsius,
    val grind: GrindSize,
    val brewSeconds: BrewSeconds,
) {
    enum class GrindSize { FINE, MEDIUM, COARSE }

    @JvmInline
    value class Name(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "Recipe name cannot be blank" }
        }

        override fun toString(): String = value
    }
}

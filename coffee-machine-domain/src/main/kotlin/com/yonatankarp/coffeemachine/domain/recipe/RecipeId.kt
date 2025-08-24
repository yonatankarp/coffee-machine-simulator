package com.yonatankarp.coffeemachine.domain.recipe

import java.util.UUID

@JvmInline
value class RecipeId(
    val value: UUID,
) {
    companion object {
        fun new() = RecipeId(UUID.randomUUID())

        fun from(value: String) = RecipeId(UUID.fromString(value))
    }
}

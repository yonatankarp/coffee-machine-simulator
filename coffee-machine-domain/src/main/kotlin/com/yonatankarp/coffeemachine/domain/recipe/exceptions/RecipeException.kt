package com.yonatankarp.coffeemachine.domain.recipe.exceptions

import com.yonatankarp.coffeemachine.domain.recipe.Recipe.Name.entries

sealed class RecipeException(
    message: String,
) : Exception(message) {
    class UnknownRecipeName(
        name: String,
    ) : RecipeException("Unknown recipe: $name. Expected one of: ${entries.joinToString { it.displayName }}")
}

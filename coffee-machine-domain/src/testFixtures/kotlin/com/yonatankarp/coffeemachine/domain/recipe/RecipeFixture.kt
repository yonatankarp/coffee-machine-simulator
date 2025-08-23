package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds

object RecipeFixture {
    val espresso =
        Recipe(
            name = Recipe.Name("espresso"),
            water = Milliliters(30.0),
            beans = Grams(9.0),
            temperature = Celsius(93.0),
            grind = Recipe.GrindSize.FINE,
            brewSeconds = BrewSeconds(Seconds(28.0)),
        )
}

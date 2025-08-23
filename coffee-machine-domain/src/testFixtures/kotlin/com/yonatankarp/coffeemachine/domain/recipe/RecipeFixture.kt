package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.BrewSecondsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.CelsiusFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture

object RecipeFixture {
    val espresso: Recipe
        get() =
            Recipe(
                name = Recipe.Name.ESPRESSO,
                water = MillilitersFixture.thirty,
                beans = GramsFixture.nine,
                temperature = CelsiusFixture.nintyThree,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twentyEight,
            )

    val americano: Recipe
        get() =
            Recipe(
                name = Recipe.Name.AMERICANO,
                water = MillilitersFixture.oneHundredTwenty,
                beans = GramsFixture.nine,
                temperature = CelsiusFixture.nintyThree,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twentyEight,
            )
}

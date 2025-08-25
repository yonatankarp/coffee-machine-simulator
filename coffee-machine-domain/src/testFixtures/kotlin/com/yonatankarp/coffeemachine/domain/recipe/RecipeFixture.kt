package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.BrewSecondsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.CelsiusFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture

object RecipeFixture {
    val espresso: Recipe
        get() =
            Recipe(
                id = RecipeId.from("2dd641d1-b628-46b8-b3db-54b2fb409af2"),
                name = Recipe.Name.ESPRESSO,
                water = MillilitersFixture.thirty,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyThree,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twentyFive,
            )

    val ristretto: Recipe
        get() =
            Recipe(
                id = RecipeId.from("9b74c4a3-c989-447e-8018-2d5aade22ef8"),
                name = Recipe.Name.RISTRETTO,
                water = MillilitersFixture.twentyTwo,
                beans = GramsFixture.sixteen,
                temperature = CelsiusFixture.ninetyFour,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twenty,
            )

    val lungo: Recipe
        get() =
            Recipe(
                id = RecipeId.from("80c62d5f-522d-457e-97ef-f0246a42514c"),
                name = Recipe.Name.LUNGO,
                water = MillilitersFixture.sixty,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyTwo,
                grind = Recipe.GrindSize.MEDIUM,
                brewSeconds = BrewSecondsFixture.thirtyFive,
            )

    val americano: Recipe
        get() =
            Recipe(
                id = RecipeId.from("0da3afe6-d794-49a7-9502-eae556c7af72"),
                name = Recipe.Name.AMERICANO,
                water = MillilitersFixture.oneHundredTwenty,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyThree,
                grind = Recipe.GrindSize.MEDIUM,
                brewSeconds = BrewSecondsFixture.twentyFive,
            )

    val longBlack: Recipe
        get() =
            Recipe(
                id = RecipeId.from("f3384644-a249-49fe-b104-349277015ea1"),
                name = Recipe.Name.LONG_BLACK,
                water = MillilitersFixture.oneHundred,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyThree,
                grind = Recipe.GrindSize.MEDIUM,
                brewSeconds = BrewSecondsFixture.twentyFive,
            )

    val latte: Recipe
        get() =
            Recipe(
                id = RecipeId.from("ea54c297-048a-45f9-be19-7566745d286f"),
                name = Recipe.Name.LATTE,
                water = MillilitersFixture.thirtyFive,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyThree,
                grind = Recipe.GrindSize.MEDIUM,
                brewSeconds = BrewSecondsFixture.thirty,
            )

    val cappuccino: Recipe
        get() =
            Recipe(
                id = RecipeId.from("9cff2ba3-0147-432c-b92d-6dba3fe290a4"),
                name = Recipe.Name.CAPPUCCINO,
                water = MillilitersFixture.thirty,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyThree,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twentyFive,
            )

    val flatWhite: Recipe
        get() =
            Recipe(
                id = RecipeId.from("67a94fb4-66ef-4e06-86a5-c330773cec69"),
                name = Recipe.Name.FLAT_WHITE,
                water = MillilitersFixture.thirty,
                beans = GramsFixture.eighteen,
                temperature = CelsiusFixture.ninetyTwo,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twentyFive,
            )

    val doubleEspresso: Recipe
        get() =
            Recipe(
                id = RecipeId.from("9a0ce96f-e5b3-417a-90e3-520d176a2d52"),
                name = Recipe.Name.DOUBLE_ESPRESSO,
                water = MillilitersFixture.sixty,
                beans = GramsFixture.thirtySix,
                temperature = CelsiusFixture.ninetyFour,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.thirtyFive,
            )

    val cortado: Recipe
        get() =
            Recipe(
                id = RecipeId.from("a213f3cc-a5ff-4ab8-b44e-d00a07db2cdc"),
                name = Recipe.Name.CORTADO,
                water = MillilitersFixture.twentyFive,
                beans = GramsFixture.sixteen,
                temperature = CelsiusFixture.ninetyTwo,
                grind = Recipe.GrindSize.FINE,
                brewSeconds = BrewSecondsFixture.twenty,
            )
}

package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds

object BrewSecondsFixture {
    val twenty get() = BrewSeconds(SecondsFixture.twenty)
    val twentyFive get() = BrewSeconds(SecondsFixture.twentyFive)
    val thirty get() = BrewSeconds(SecondsFixture.thirty)
    val thirtyFive get() = BrewSeconds(SecondsFixture.thirtyFive)
}

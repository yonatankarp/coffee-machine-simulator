package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds

@JvmInline
value class BrewSeconds(
    val second: Seconds,
) {
    init {
        require(second.value.toInt() in 1..120) { "Brew must be between 1 and 120 seconds" }
    }
}

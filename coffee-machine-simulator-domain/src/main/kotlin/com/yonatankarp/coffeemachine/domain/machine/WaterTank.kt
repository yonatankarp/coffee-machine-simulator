package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

data class WaterTank(
    val capacity: Milliliters,
    val current: Milliliters,
) {
    init {
        require(current.value <= capacity.value) { "Water overflow" }
    }

    fun consume(amount: Milliliters) =
        copy(
            current =
                Milliliters(current.value - amount.value),
        )

    fun refill() = copy(current = capacity)
}

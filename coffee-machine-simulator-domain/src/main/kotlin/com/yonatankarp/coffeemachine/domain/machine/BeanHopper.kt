package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams

data class BeanHopper(
    val capacity: Grams,
    val current: Grams,
) {
    init {
        require(current.value <= capacity.value) { "Beans overflow" }
    }

    fun consume(amount: Grams) = copy(current = Grams(current.value - amount.value))

    fun refill() = copy(current = capacity)
}

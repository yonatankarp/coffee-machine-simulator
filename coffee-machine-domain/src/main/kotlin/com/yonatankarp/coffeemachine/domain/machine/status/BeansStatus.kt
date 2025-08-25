package com.yonatankarp.coffeemachine.domain.machine.status

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams

data class BeansStatus(
    val current: Grams,
    val capacity: Grams,
) {
    fun remainingRatio(): Int {
        if (capacity <= Grams.ZERO) return 0
        val ratio = current.value / capacity.value
        return (ratio.coerceIn(0.0, 1.0) * 100).toInt()
    }

    operator fun plus(beans: BeansStatus) =
        BeansStatus(
            current = (this.current + beans.current).coerceAtMost(this.capacity),
            capacity = this.capacity,
        )

    operator fun minus(beans: BeansStatus) =
        BeansStatus(
            current = (this.current - beans.current).coerceAtLeast(Grams.ZERO),
            capacity = this.capacity,
        )
}

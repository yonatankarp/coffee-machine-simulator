package com.yonatankarp.coffeemachine.domain.machine.status

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

data class WaterStatus(
    val current: Milliliters,
    val capacity: Milliliters,
) {
    fun remainingRatio(): Int {
        if (capacity <= Milliliters.ZERO) return 0
        val ratio = current.value / capacity.value
        return (ratio.coerceIn(0.0, 1.0) * 100).toInt()
    }

    operator fun plus(water: WaterStatus) =
        WaterStatus(
            current = (this.current + water.current).coerceAtMost(this.capacity),
            capacity = this.capacity,
        )

    operator fun minus(water: WaterStatus) =
        WaterStatus(
            current = (this.current - water.current).coerceAtLeast(Milliliters.ZERO),
            capacity = this.capacity,
        )
}

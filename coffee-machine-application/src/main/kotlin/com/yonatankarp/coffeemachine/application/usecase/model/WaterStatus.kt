package com.yonatankarp.coffeemachine.application.usecase.model

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
}

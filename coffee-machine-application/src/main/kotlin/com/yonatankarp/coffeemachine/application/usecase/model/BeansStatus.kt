package com.yonatankarp.coffeemachine.application.usecase.model

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
}

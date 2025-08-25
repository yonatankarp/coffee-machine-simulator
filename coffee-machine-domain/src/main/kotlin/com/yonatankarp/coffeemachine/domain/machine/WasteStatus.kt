package com.yonatankarp.coffeemachine.domain.machine

data class WasteStatus(
    val currentPucks: Int,
    val capacityPucks: Int,
) {
    fun remainingRatio(): Int {
        if (currentPucks <= 0) return 0
        val ratio = currentPucks / capacityPucks
        return ratio.coerceIn(0, 100)
    }
}

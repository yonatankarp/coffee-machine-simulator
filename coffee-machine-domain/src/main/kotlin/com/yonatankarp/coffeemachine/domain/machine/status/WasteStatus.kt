package com.yonatankarp.coffeemachine.domain.machine.status

data class WasteStatus(
    val currentPucks: Int,
    val capacityPucks: Int,
) {
    fun remainingRatio(): Int {
        if (currentPucks <= 0) return 0
        val ratio = currentPucks / capacityPucks
        return ratio.coerceIn(0, 100)
    }

    operator fun minus(other: WasteStatus) =
        WasteStatus(
            currentPucks = (this.currentPucks - other.currentPucks).coerceAtLeast(0),
            capacityPucks = this.capacityPucks,
        )

    operator fun plus(other: WasteStatus) =
        WasteStatus(
            currentPucks = (this.currentPucks + other.currentPucks).coerceAtMost(this.capacityPucks),
            capacityPucks = this.capacityPucks,
        )
}

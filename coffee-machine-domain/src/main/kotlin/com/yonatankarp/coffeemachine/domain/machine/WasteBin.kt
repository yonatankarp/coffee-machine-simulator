package com.yonatankarp.coffeemachine.domain.machine

data class WasteBin(
    val capacityPucks: Int,
    val currentPucks: Int,
) {
    init {
        require(capacityPucks > 0) { "Capacity must be positive" }
        require(currentPucks in 0..capacityPucks) { "Current pucks must be between 0 and capacity ($capacityPucks), but was $currentPucks" }
    }

    fun addPuck() =
        copy(
            currentPucks =
                (currentPucks + 1).also {
                    require(it <= capacityPucks) { "Waste bin full" }
                },
        )

    fun empty() = copy(currentPucks = 0)
}

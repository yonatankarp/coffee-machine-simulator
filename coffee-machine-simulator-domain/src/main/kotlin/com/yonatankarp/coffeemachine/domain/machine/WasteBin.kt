package com.yonatankarp.coffeemachine.domain.machine

data class WasteBin(
    val capacityPucks: Int,
    val currentPucks: Int,
) {
    init {
        require(capacityPucks > 0)
        require(currentPucks in 0..capacityPucks)
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

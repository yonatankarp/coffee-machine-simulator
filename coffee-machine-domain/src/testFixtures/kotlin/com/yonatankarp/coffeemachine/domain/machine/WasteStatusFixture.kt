package com.yonatankarp.coffeemachine.domain.machine

object WasteStatusFixture {
    val empty: WasteStatus
        get() =
            WasteStatus(
                currentPucks = 0,
                capacityPucks = 10,
            )

    val used: WasteStatus
        get() =
            WasteStatus(
                currentPucks = 3,
                capacityPucks = 10,
            )

    val full: WasteStatus
        get() =
            WasteStatus(
                currentPucks = 10,
                capacityPucks = 10,
            )
}

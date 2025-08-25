package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture

object WaterStatusFixture {
    val empty: WaterStatus
        get() =
            WaterStatus(
                current = Milliliters.ZERO,
                capacity = MillilitersFixture.oneThousand,
            )

    val used: WaterStatus
        get() =
            WaterStatus(
                current = MillilitersFixture.fiveHundred,
                capacity = MillilitersFixture.oneThousand,
            )

    val full: WaterStatus
        get() =
            WaterStatus(
                current = MillilitersFixture.oneThousand,
                capacity = MillilitersFixture.oneThousand,
            )
}

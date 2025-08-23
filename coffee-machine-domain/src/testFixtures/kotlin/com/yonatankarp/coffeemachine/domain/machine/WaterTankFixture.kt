package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture.fiveHundred

object WaterTankFixture {
    val empty get() = WaterTank(capacity = MillilitersFixture.oneThousand, current = Milliliters.ZERO)
    val used get() = WaterTank(capacity = MillilitersFixture.oneThousand, current = fiveHundred)
    val full get() = WaterTank(capacity = MillilitersFixture.oneThousand, current = MillilitersFixture.oneThousand)
}

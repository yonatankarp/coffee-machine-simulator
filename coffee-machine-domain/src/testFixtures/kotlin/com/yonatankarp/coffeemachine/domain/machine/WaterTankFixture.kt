package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture.fiveHundred
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture.oneThousand

object WaterTankFixture {
    val empty = WaterTank(capacity = oneThousand, current = Milliliters.ZERO)
    val used = WaterTank(capacity = oneThousand, current = fiveHundred)
    val full = WaterTank(capacity = oneThousand, current = oneThousand)
}

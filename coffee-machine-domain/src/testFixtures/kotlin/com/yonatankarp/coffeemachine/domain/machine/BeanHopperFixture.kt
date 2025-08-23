package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture.fiveHundred
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture.threeHundred

object BeanHopperFixture {
    val empty get() = BeanHopper(capacity = fiveHundred, current = Grams.ZERO)
    val used get() = BeanHopper(capacity = fiveHundred, current = threeHundred)
    val full get() = BeanHopper(capacity = fiveHundred, current = fiveHundred)
}

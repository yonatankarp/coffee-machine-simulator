package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture.fiveHundred
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture.threeHundred

object BeanHopperFixture {
    val empty = BeanHopper(capacity = fiveHundred, current = Grams.ZERO)
    val used = BeanHopper(capacity = fiveHundred, current = threeHundred)
    val full = BeanHopper(capacity = fiveHundred, current = fiveHundred)
}

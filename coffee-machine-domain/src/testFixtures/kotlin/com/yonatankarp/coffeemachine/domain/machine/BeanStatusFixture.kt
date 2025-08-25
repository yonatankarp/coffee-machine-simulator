package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture

object BeanStatusFixture {
    val empty: BeansStatus
        get() =
            BeansStatus(
                current = Grams.ZERO,
                capacity = GramsFixture.fiveHundred,
            )

    val used: BeansStatus
        get() =
            BeansStatus(
                current = GramsFixture.threeHundred,
                capacity = GramsFixture.fiveHundred,
            )

    val full: BeansStatus
        get() =
            BeansStatus(
                current = GramsFixture.fiveHundred,
                capacity = GramsFixture.fiveHundred,
            )
}

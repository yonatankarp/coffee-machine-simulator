package com.yonatankarp.coffeemachine.domain.machine

object WasteBinFixture {
    val empty get() = WasteBin(capacityPucks = 10, currentPucks = 0)
    val used get() = WasteBin(capacityPucks = 10, currentPucks = 3)
    val full get() = WasteBin(capacityPucks = 10, currentPucks = 10)
}

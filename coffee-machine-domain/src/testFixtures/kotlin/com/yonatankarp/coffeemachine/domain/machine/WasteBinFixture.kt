package com.yonatankarp.coffeemachine.domain.machine

object WasteBinFixture {
    val empty = WasteBin(capacityPucks = 10, currentPucks = 0)
    val used = WasteBin(capacityPucks = 10, currentPucks = 3)
    val full = WasteBin(capacityPucks = 10, currentPucks = 10)
}

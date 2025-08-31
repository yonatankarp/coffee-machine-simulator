package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.brew.Brew

fun interface AdvanceBrew {
    operator fun invoke(brewId: Brew.Id): Brew
}

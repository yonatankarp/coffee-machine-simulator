package com.yonatankarp.coffeemachine.application.usecase.model

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams

data class BeansStatus(
    val current: Grams,
    val capacity: Grams,
)

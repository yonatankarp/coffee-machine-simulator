package com.yonatankarp.coffeemachine.application.usecase.model

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

data class WaterStatus(
    val current: Milliliters,
    val capacity: Milliliters,
)

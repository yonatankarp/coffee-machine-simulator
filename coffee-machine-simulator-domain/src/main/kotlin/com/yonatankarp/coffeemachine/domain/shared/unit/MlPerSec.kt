package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class MlPerSec(
    override val value: Double,
) : Units<MlPerSec> {
    init {
        require(value >= 0.0) { "Flow cannot be negative" }
    }

    override fun compareTo(other: MlPerSec): Int = value.compareTo(other.value)

    operator fun plus(other: MlPerSec): MlPerSec = MlPerSec(value + other.value)

    operator fun minus(other: MlPerSec): MlPerSec = MlPerSec(max(0.0, value - other.value))

    operator fun times(k: Double): MlPerSec = MlPerSec(value * k)

    operator fun div(k: Double): MlPerSec {
        require(k != 0.0) { "Cannot divide by zero" }
        return MlPerSec(value / k)
    }

    override fun toString(): String = value.format("%.2f ml/s")

    companion object {
        val ZERO = MlPerSec(0.0)
    }
}

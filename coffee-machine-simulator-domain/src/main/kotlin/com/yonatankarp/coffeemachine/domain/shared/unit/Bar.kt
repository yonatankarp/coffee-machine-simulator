package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Bar(
    override val value: Double,
) : Units<Bar> {
    init {
        require(value >= 0.0) { "Pressure cannot be negative" }
    }

    override fun compareTo(other: Bar): Int = value.compareTo(other.value)

    operator fun plus(other: Bar): Bar = Bar(value + other.value)

    operator fun minus(other: Bar): Bar = Bar(max(0.0, value - other.value))

    operator fun times(k: Double): Bar = Bar(value * k)

    operator fun div(k: Double): Bar {
        require(k != 0.0) { "Cannot divide by zero" }
        return Bar(value / k)
    }

    override fun toString(): String = value.format("%.2f bar")

    companion object {
        val ZERO = Bar(0.0)
    }
}

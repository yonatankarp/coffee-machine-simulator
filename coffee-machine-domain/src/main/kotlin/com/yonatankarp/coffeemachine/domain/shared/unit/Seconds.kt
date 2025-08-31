package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Seconds(
    override val value: Double,
) : Units<Seconds> {
    init {
        require(value >= 0.0) { "Time cannot be negative" }
    }

    override fun compareTo(other: Seconds): Int = value.compareTo(other.value)

    operator fun plus(other: Seconds): Seconds = Seconds(value + other.value)

    operator fun minus(other: Seconds): Seconds = Seconds(max(0.0, value - other.value))

    operator fun times(k: Double): Seconds = Seconds(value * k)

    operator fun div(k: Double): Seconds {
        require(k != 0.0) { "Cannot divide by zero" }
        return Seconds(value / k)
    }

    override fun toString(): String = value.format("%.1fs")

    companion object {
        val ZERO = Seconds(0.0)

        fun from(millis: Long): Seconds {
            require(millis >= 0) { "Duration cannot be negative" }
            return Seconds(millis / 1000.0)
        }
    }
}

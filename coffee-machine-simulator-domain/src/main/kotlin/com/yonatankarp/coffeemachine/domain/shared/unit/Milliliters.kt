package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Milliliters(
    override val value: Double,
) : Units<Milliliters> {
    init {
        require(value >= 0.0) { "Volume cannot be negative" }
    }

    override fun compareTo(other: Milliliters): Int = value.compareTo(other.value)

    operator fun plus(other: Milliliters): Milliliters = Milliliters(value + other.value)

    operator fun minus(other: Milliliters): Milliliters = Milliliters(max(0.0, value - other.value))

    operator fun times(k: Double): Milliliters = Milliliters(value * k)

    operator fun div(k: Double): Milliliters {
        require(k != 0.0) { "Cannot divide by zero" }
        return Milliliters(value / k)
    }

    operator fun div(other: Milliliters): Double {
        require(other.value != 0.0) { "Cannot divide by zero ml" }
        return value / other.value
    }

    override fun toString(): String = value.format("%.0f ml")

    companion object {
        val ZERO = Milliliters(0.0)

        fun fromLiters(liters: Double): Milliliters {
            require(liters >= 0.0) { "Volume cannot be negative" }
            return Milliliters(liters * 1000.0)
        }
    }
}

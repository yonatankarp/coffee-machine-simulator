package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Milliliters(
    override val value: Double,
) : Units,
    Comparable<Milliliters> {
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

    fun approxEquals(
        other: Milliliters,
        epsilon: Double = DEFAULT_EPSILON,
    ): Boolean = value.approxEquals(other.value, epsilon)

    fun coerceAtLeast(minimum: Milliliters): Milliliters = if (this < minimum) minimum else this

    fun coerceAtMost(maximum: Milliliters): Milliliters = if (this > maximum) maximum else this

    fun coerceIn(
        minimum: Milliliters,
        maximum: Milliliters,
    ): Milliliters =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
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

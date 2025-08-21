package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format

@JvmInline
value class Celsius(override val value: Double) : Units, Comparable<Celsius> {
    init {
        require(value >= -273.15) { "Temperature below absolute zero" }
    }

    override fun compareTo(other: Celsius): Int = value.compareTo(other.value)

    operator fun plus(other: Celsius): Celsius = Celsius(value + other.value)
    operator fun minus(other: Celsius): Celsius = Celsius(value - other.value)
    operator fun times(k: Double): Celsius = Celsius(value * k)
    operator fun div(k: Double): Celsius {
        require(k != 0.0) { "Cannot divide by zero" }
        return Celsius(value / k)
    }

    fun approxEquals(other: Celsius, epsilon: Double = DEFAULT_EPSILON): Boolean =
        value.approxEquals(other.value, epsilon)

    fun coerceAtLeast(minimum: Celsius): Celsius = if (this < minimum) minimum else this
    fun coerceAtMost(maximum: Celsius): Celsius = if (this > maximum) maximum else this
    fun coerceIn(minimum: Celsius, maximum: Celsius): Celsius =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    override fun toString(): String = value.format("%.1f°C")

    companion object {
        val ZERO = Celsius(0.0)
    }
}

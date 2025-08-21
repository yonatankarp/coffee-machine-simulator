package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Grams(
    override val value: Double,
) : Units,
    Comparable<Grams> {
    init {
        require(value >= 0.0) { "Mass cannot be negative" }
    }

    override fun compareTo(other: Grams): Int = value.compareTo(other.value)

    operator fun plus(other: Grams): Grams = Grams(value + other.value)

    operator fun minus(other: Grams): Grams = Grams(max(0.0, value - other.value))

    operator fun times(k: Double): Grams = Grams(value * k)

    operator fun div(k: Double): Grams {
        require(k != 0.0) { "Cannot divide by zero" }
        return Grams(value / k)
    }

    operator fun div(other: Grams): Double {
        require(other.value != 0.0) { "Cannot divide by zero grams" }
        return value / other.value
    }

    fun approxEquals(
        other: Grams,
        epsilon: Double = DEFAULT_EPSILON,
    ): Boolean = value.approxEquals(other.value, epsilon)

    fun coerceAtLeast(minimum: Grams): Grams = if (this < minimum) minimum else this

    fun coerceAtMost(maximum: Grams): Grams = if (this > maximum) maximum else this

    fun coerceIn(
        minimum: Grams,
        maximum: Grams,
    ): Grams =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    override fun toString(): String = value.format("%.1f g")

    companion object {
        val ZERO = Grams(0.0)
    }
}

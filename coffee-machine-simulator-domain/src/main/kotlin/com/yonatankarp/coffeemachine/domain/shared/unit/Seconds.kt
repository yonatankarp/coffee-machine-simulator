package com.yonatankarp.coffeemachine.domain.shared.unit

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import kotlin.math.max

@JvmInline
value class Seconds(override val value: Double) : Units, Comparable<Seconds> {
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

    /** Dimensionless ratio (e.g., phase scaling). */
    operator fun div(other: Seconds): Double {
        require(other.value != 0.0) { "Cannot divide by zero seconds" }
        return value / other.value
    }

    fun approxEquals(other: Seconds, epsilon: Double = DEFAULT_EPSILON): Boolean =
        value.approxEquals(other.value, epsilon)

    fun coerceAtLeast(minimum: Seconds): Seconds = if (this < minimum) minimum else this
    fun coerceAtMost(maximum: Seconds): Seconds = if (this > maximum) maximum else this
    fun coerceIn(minimum: Seconds, maximum: Seconds): Seconds =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    override fun toString(): String = value.format("%.1f s")

    companion object {
        val ZERO = Seconds(0.0)
        fun fromMillis(millis: Long): Seconds {
            require(millis >= 0) { "Duration cannot be negative" }
            return Seconds(millis / 1000.0)
        }
    }
}

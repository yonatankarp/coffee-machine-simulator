package com.yonatankarp.coffeemachine.domain.shared.unit

import kotlin.math.abs

internal const val DEFAULT_EPSILON: Double = 1e-6

sealed interface Units {
    val value: Double

    fun Double.approxEquals(
        other: Double,
        epsilon: Double = DEFAULT_EPSILON,
    ): Boolean = abs(this - other) <= epsilon

    data class Range<T>(
        override val start: T,
        override val endInclusive: T,
    ) : ClosedRange<T> where T : Units, T : Comparable<T> {
        init {
            require(start <= endInclusive) { "Range start must be <= endInclusive" }
        }
    }

    fun <T> minOf(
        a: T,
        b: T,
    ): T where T : Units, T : Comparable<T> = if (a <= b) a else b

    fun <T> maxOf(
        a: T,
        b: T,
    ): T where T : Units, T : Comparable<T> = if (a >= b) a else b
}

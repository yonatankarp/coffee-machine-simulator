package com.yonatankarp.coffeemachine.domain.shared.unit

sealed interface Units<T : Units<T>> : Comparable<T> {
    val value: Double

    override fun compareTo(other: T): Int = value.compareTo(other.value)
}

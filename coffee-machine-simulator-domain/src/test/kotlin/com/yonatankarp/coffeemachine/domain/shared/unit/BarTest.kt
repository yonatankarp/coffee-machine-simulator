package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BarTest {
    @Test
    fun `non-negative pressure allowed and uses two decimals`() {
        // Given
        val input = 9.876

        // When
        val pressure = Bar(input)
        val rendered = pressure.toString()

        // Then
        rendered shouldBe "9.88 bar"
    }

    @Test
    fun `reject negative pressure`() {
        // Given
        val input = -0.0001

        // When
        val ex = shouldThrow<IllegalArgumentException> { Bar(input) }

        // Then
        ex.message shouldBe "Pressure cannot be negative"
    }

    @Test
    fun `zero pressure is allowed`() {
        // Given
        val input = 0.0

        // When
        val result = Bar(input)

        // Then
        result shouldBe Bar.ZERO
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `should add two Bar values`() {
            // Given
            val a = Bar(5.0)
            val b = Bar(3.0)

            // When
            val result = a + b

            // Then
            result shouldBe Bar(8.0)
        }

        @Test
        fun `should subtract two Bar values`() {
            // Given
            val a = Bar(5.0)
            val b = Bar(3.0)

            // When
            val result = a - b

            // Then
            result shouldBe Bar(2.0)
        }

        @Test
        fun `should multiply Bar by a scalar`() {
            // Given
            val a = Bar(5.0)
            val scalar = 2.0

            // When
            val result = a * scalar

            // Then
            result shouldBe Bar(10.0)
        }

        @Test
        fun `should divide Bar by a non-zero scalar`() {
            // Given
            val a = Bar(10.0)
            val scalar = 2.0

            // When
            val result = a / scalar

            // Then
            result shouldBe Bar(5.0)
        }

        @Test
        fun `should throw when dividing by zero`() {
            // Given
            val a = Bar(10.0)
            val scalar = 0.0

            // When
            val ex = shouldThrow<IllegalArgumentException> { a / scalar }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }

        @Test
        fun `should compare two Bar values`() {
            // Given
            val a = Bar(5.0)
            val b = Bar(3.0)

            // When
            val comparison = a.compareTo(b)

            // Then
            comparison shouldBe 1
        }

        @Test
        fun `should return minimum of two Bar values`() {
            // Given
            val a = Bar(5.0)
            val b = Bar(3.0)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe b
        }

        @Test
        fun `should return maximum of two Bar values`() {
            // Given
            val a = Bar(5.0)
            val b = Bar(3.0)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe a
        }
    }
}

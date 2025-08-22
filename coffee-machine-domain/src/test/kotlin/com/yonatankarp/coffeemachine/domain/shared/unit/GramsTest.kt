package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GramsTest {
    @Test
    fun `formats with one decimal and g suffix`() {
        // Given
        val input = 18.04

        // When
        val grams = Grams(input)
        val rendered = grams.toString()

        // Then
        rendered shouldBe "18.0 g"
    }

    @Test
    fun `reject negative mass`() {
        // Given
        val input = -0.001

        // When
        val ex = shouldThrow<IllegalArgumentException> { Grams(input) }

        // Then
        ex.message shouldBe "Mass cannot be negative"
    }

    @Test
    fun `ZERO constant equals 0_0 grams`() {
        // Given
        val expected = Grams(0.0)

        // When
        val zero = Grams.ZERO

        // Then
        zero shouldBe expected
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `plus adds values`() {
            // Given
            val a = Grams(7.5)
            val b = Grams(2.4)

            // When
            val sum = a + b

            // Then
            sum shouldBe Grams(9.9)
        }

        @Test
        fun `minus subtracts when result positive`() {
            // Given
            val a = Grams(10.0)
            val b = Grams(3.0)

            // When
            val diff = a - b

            // Then
            diff shouldBe Grams(7.0)
        }

        @Test
        fun `minus saturates at zero when result negative`() {
            // Given
            val a = Grams(7.0)
            val b = Grams(8.0)

            // When
            val diff = a - b

            // Then
            diff shouldBe Grams(0.0)
        }

        @Test
        fun `times scales by factor`() {
            // Given
            val g = Grams(12.0)
            val k = 1.5

            // When
            val scaled = g * k

            // Then
            scaled shouldBe Grams(18.0)
        }
    }

    @Nested
    inner class DivisionByScalar {
        @Test
        fun `div scales down by factor`() {
            // Given
            val g = Grams(12.0)
            val k = 2.0

            // When
            val scaled = g / k

            // Then
            scaled shouldBe Grams(6.0)
        }

        @Test
        fun `div by zero throws`() {
            // Given
            val g = Grams(12.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { g / 0.0 }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }
    }

    @Nested
    inner class RatioDivision {
        @Test
        fun `div by grams returns dimensionless ratio`() {
            // Given
            val numerator = Grams(20.0)
            val denominator = Grams(4.0)

            // When
            val ratio = numerator / denominator

            // Then
            ratio shouldBe 5.0
        }

        @Test
        fun `div by zero grams throws`() {
            // Given
            val numerator = Grams(20.0)
            val denominator = Grams(0.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { numerator / denominator }

            // Then
            ex.message shouldBe "Cannot divide by zero grams"
        }

        @Test
        fun `should return minimum of two Gram values`() {
            // Given
            val a = Grams(10.0)
            val b = Grams(5.0)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe Grams(5.0)
        }

        @Test
        fun `should return maximum of two Gram values`() {
            // Given
            val a = Grams(10.0)
            val b = Grams(5.0)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe Grams(10.0)
        }
    }
}

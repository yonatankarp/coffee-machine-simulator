package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MillilitersTest {
    @Test
    fun `formats as integer ml`() {
        // Given
        val input = 59.6

        // When
        val ml = Milliliters(input)
        val rendered = ml.toString()

        // Then
        rendered shouldBe "60 ml"
    }

    @Test
    fun `reject negative volume`() {
        // Given
        val input = -1.0

        // When
        val ex = shouldThrow<IllegalArgumentException> { Milliliters(input) }

        // Then
        ex.message shouldBe "Volume cannot be negative"
    }

    @Test
    fun `ZERO constant equals 0 ml`() {
        // Given
        val expected = Milliliters(0.0)

        // When
        val zero = Milliliters.ZERO

        // Then
        zero shouldBe expected
    }

    @Nested
    inner class Factory {
        @Test
        fun `fromLiters converts non-negative liters`() {
            // Given
            val liters = 0.25

            // When
            val ml = Milliliters.fromLiters(liters)

            // Then
            ml shouldBe Milliliters(250.0)
        }

        @Test
        fun `fromLiters rejects negative liters`() {
            // Given
            val liters = -0.1

            // When
            val ex = shouldThrow<IllegalArgumentException> { Milliliters.fromLiters(liters) }

            // Then
            ex.message shouldBe "Volume cannot be negative"
        }
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `plus adds values`() {
            // Given
            val a = Milliliters(120.0)
            val b = Milliliters(30.0)

            // When
            val sum = a + b

            // Then
            sum shouldBe Milliliters(150.0)
        }

        @Test
        fun `minus subtracts when result positive`() {
            // Given
            val a = Milliliters(80.0)
            val b = Milliliters(30.0)

            // When
            val diff = a - b

            // Then
            diff shouldBe Milliliters(50.0)
        }

        @Test
        fun `minus saturates at zero when result negative`() {
            // Given
            val a = Milliliters(30.0)
            val b = Milliliters(80.0)

            // When
            val diff = a - b

            // Then
            diff shouldBe Milliliters(0.0)
        }

        @Test
        fun `times scales by factor`() {
            // Given
            val ml = Milliliters(40.0)
            val k = 2.5

            // When
            val scaled = ml * k

            // Then
            scaled shouldBe Milliliters(100.0)
        }
    }

    @Nested
    inner class DivisionByScalar {
        @Test
        fun `div scales down by factor`() {
            // Given
            val ml = Milliliters(100.0)
            val k = 4.0

            // When
            val scaled = ml / k

            // Then
            scaled shouldBe Milliliters(25.0)
        }

        @Test
        fun `div by zero throws`() {
            // Given
            val ml = Milliliters(100.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { ml / 0.0 }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }
    }

    @Nested
    inner class RatioDivision {
        @Test
        fun `div by milliliters returns dimensionless ratio`() {
            // Given
            val numerator = Milliliters(200.0)
            val denominator = Milliliters(50.0)

            // When
            val ratio = numerator / denominator

            // Then
            ratio shouldBe 4.0
        }

        @Test
        fun `div by zero ml throws`() {
            // Given
            val numerator = Milliliters(200.0)
            val denominator = Milliliters(0.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { numerator / denominator }

            // Then
            ex.message shouldBe "Cannot divide by zero ml"
        }

        @Test
        fun `should return minimum of two Milliliters values`() {
            // Given
            val a = Milliliters(150.0)
            val b = Milliliters(100.0)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe b
        }

        @Test
        fun `should return maximum of two Milliliters values`() {
            // Given
            val a = Milliliters(150.0)
            val b = Milliliters(100.0)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe a
        }
    }
}

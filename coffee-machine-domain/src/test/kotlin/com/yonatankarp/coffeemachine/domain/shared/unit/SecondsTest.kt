package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SecondsTest {
    @Test
    fun `formats with one decimal and s suffix`() {
        // Given
        val input = 1.25

        // When
        val s = Seconds(input)
        val rendered = s.toString()

        // Then
        rendered shouldBe "1.3s"
    }

    @Test
    fun `reject negative time`() {
        // Given
        val input = -0.0001

        // When
        val ex = shouldThrow<IllegalArgumentException> { Seconds(input) }

        // Then
        ex.message shouldBe "Time cannot be negative"
    }

    @Test
    fun `ZERO constant equals 0_0 seconds`() {
        // Given
        val expected = Seconds(0.0)

        // When
        val zero = Seconds.ZERO

        // Then
        zero shouldBe expected
    }

    @Nested
    inner class FactoryFromMillis {
        @Test
        fun `fromMillis converts non-negative millis`() {
            // Given
            val millis = 2500L

            // When
            val seconds = Seconds.from(millis)

            // Then
            seconds shouldBe Seconds(2.5)
        }

        @Test
        fun `fromMillis rejects negative durations`() {
            // Given
            val millis = -1L

            // When
            val ex = shouldThrow<IllegalArgumentException> { Seconds.from(millis) }

            // Then
            ex.message shouldBe "Duration cannot be negative"
        }
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `plus adds values`() {
            // Given
            val a = Seconds(10.2)
            val b = Seconds(2.3)

            // When
            val sum = a + b

            // Then
            sum shouldBe Seconds(12.5)
        }

        @Test
        fun `minus subtracts when result positive`() {
            // Given
            val a = Seconds(20.0)
            val b = Seconds(7.5)

            // When
            val diff = a - b

            // Then
            diff shouldBe Seconds(12.5)
        }

        @Test
        fun `minus saturates at zero when result negative`() {
            // Given
            val a = Seconds(3.0)
            val b = Seconds(5.0)

            // When
            val diff = a - b

            // Then
            diff shouldBe Seconds(0.0)
        }

        @Test
        fun `times scales by factor`() {
            // Given
            val s = Seconds(12.0)
            val k = 1.5

            // When
            val scaled = s * k

            // Then
            scaled shouldBe Seconds(18.0)
        }
    }

    @Nested
    inner class DivisionByScalar {
        @Test
        fun `div scales down by factor`() {
            // Given
            val s = Seconds(12.0)
            val k = 2.0

            // When
            val scaled = s / k

            // Then
            scaled shouldBe Seconds(6.0)
        }

        @Test
        fun `div by zero throws`() {
            // Given
            val s = Seconds(12.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { s / 0.0 }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }

        @Test
        fun `should return minimum of two Seconds values`() {
            // Given
            val a = Seconds(5.0)
            val b = Seconds(3.0)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe b
        }

        @Test
        fun `should return maximum of two Seconds values`() {
            // Given
            val a = Seconds(5.0)
            val b = Seconds(7.0)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe b
        }
    }
}

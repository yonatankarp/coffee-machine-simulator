package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CelsiusTest {
    @Test
    fun `formats with one decimal and degree symbol`() {
        // Given
        val input = 93.456

        // When
        val temp = Celsius(input)
        val rendered = temp.toString()

        // Then
        rendered shouldBe "93.5°C"
    }

    @Test
    fun `reject temperature below absolute zero`() {
        // Given
        val input = -273.151

        // When
        val ex = shouldThrow<IllegalArgumentException> { Celsius(input) }

        // Then
        ex.message shouldBe "Temperature below absolute zero"
    }

    @Test
    fun `ZERO constant equals 0_0 Celsius`() {
        // Given
        val expected = Celsius(0.0)

        // When
        val zero = Celsius.ZERO

        // Then
        zero shouldBe expected
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `plus adds values`() {
            // Given
            val a = Celsius(10.2)
            val b = Celsius(2.3)

            // When
            val sum = a + b

            // Then
            sum shouldBe Celsius(12.5)
        }

        @Test
        fun `minus subtracts values`() {
            // Given
            val a = Celsius(20.0)
            val b = Celsius(7.5)

            // When
            val diff = a - b

            // Then
            diff shouldBe Celsius(12.5)
        }

        @Test
        fun `times scales by factor`() {
            // Given
            val t = Celsius(12.0)
            val k = 1.5

            // When
            val scaled = t * k

            // Then
            scaled shouldBe Celsius(18.0)
        }

        @Test
        fun `div scales by factor`() {
            // Given
            val t = Celsius(12.0)
            val k = 2.0

            // When
            val scaled = t / k

            // Then
            scaled shouldBe Celsius(6.0)
        }

        @Test
        fun `div by zero throws`() {
            // Given
            val t = Celsius(12.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { t / 0.0 }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }

        @Test
        fun `should return minimum of two Celsius values`() {
            // Given
            val a = Celsius(5.0)
            val b = Celsius(3.0)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe b
        }

        @Test
        fun `should return maximum of two Celsius values`() {
            // Given
            val a = Celsius(5.0)
            val b = Celsius(3.0)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe a
        }
    }
}

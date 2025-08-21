package com.yonatankarp.coffeemachine.domain.shared.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MlPerSecTest {
    @Test
    fun `formats with two decimals and ml per s suffix`() {
        // Given
        val input = 2.745

        // When
        val flow = MlPerSec(input)
        val rendered = flow.toString()

        // Then
        rendered shouldBe "2.75 ml/s"
    }

    @Test
    fun `reject negative flow`() {
        // Given
        val input = -0.01

        // When
        val ex = shouldThrow<IllegalArgumentException> { MlPerSec(input) }

        // Then
        ex.message shouldBe "Flow cannot be negative"
    }

    @Test
    fun `ZERO constant equals 0_00 ml per s`() {
        // Given
        val expected = MlPerSec(0.0)

        // When
        val zero = MlPerSec.ZERO

        // Then
        zero shouldBe expected
    }

    @Nested
    inner class Arithmetic {
        @Test
        fun `plus adds values`() {
            // Given
            val a = MlPerSec(1.2)
            val b = MlPerSec(0.3)

            // When
            val sum = a + b

            // Then
            sum shouldBe MlPerSec(1.5)
        }

        @Test
        fun `minus subtracts when result positive`() {
            // Given
            val a = MlPerSec(1.5)
            val b = MlPerSec(0.4)

            // When
            val diff = a - b

            // Then
            diff shouldBe MlPerSec(1.1)
        }

        @Test
        fun `minus saturates at zero when result negative`() {
            // Given
            val a = MlPerSec(0.5)
            val b = MlPerSec(0.8)

            // When
            val diff = a - b

            // Then
            diff shouldBe MlPerSec(0.0)
        }

        @Test
        fun `times scales by factor`() {
            // Given
            val f = MlPerSec(1.2)
            val k = 2.5

            // When
            val scaled = f * k

            // Then
            scaled shouldBe MlPerSec(3.0)
        }
    }

    @Nested
    inner class DivisionByScalar {
        @Test
        fun `div scales down by factor`() {
            // Given
            val f = MlPerSec(3.0)
            val k = 2.0

            // When
            val scaled = f / k

            // Then
            scaled shouldBe MlPerSec(1.5)
        }

        @Test
        fun `div by zero throws`() {
            // Given
            val f = MlPerSec(3.0)

            // When
            val ex = shouldThrow<IllegalArgumentException> { f / 0.0 }

            // Then
            ex.message shouldBe "Cannot divide by zero"
        }

        @Test
        fun `should return minimum of two MlPerSec values`() {
            // Given
            val a = MlPerSec(1.0)
            val b = MlPerSec(0.5)

            // When
            val min = minOf(a, b)

            // Then
            min shouldBe MlPerSec(0.5)
        }

        @Test
        fun `should return maximum of two MlPerSec values`() {
            // Given
            val a = MlPerSec(1.0)
            val b = MlPerSec(0.5)

            // When
            val max = maxOf(a, b)

            // Then
            max shouldBe MlPerSec(1.0)
        }
    }
}

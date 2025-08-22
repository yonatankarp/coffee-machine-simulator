package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WaterTankTest {
    @Test
    fun `cannot construct tank with current greater than capacity`() {
        // Given
        val capacity = Milliliters(100.0)
        val current = Milliliters(150.0)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                WaterTank(capacity = capacity, current = current)
            }

        // Then
        ex.message shouldBe "Water overflow"
    }

    @Test
    fun `consume reduces current by the requested amount`() {
        // Given
        val tank =
            WaterTank(
                capacity = Milliliters(100.0),
                current = Milliliters(80.0),
            )
        val amount = Milliliters(10.0)

        // When
        val after = tank.consume(amount)

        // Then
        after.current.value shouldBe 70.0
        tank.current.value shouldBe 80.0
    }

    @Test
    fun `consume can bring current to exactly zero`() {
        // Given
        val tank =
            WaterTank(capacity = Milliliters(50.0), current = Milliliters(20.0))
        val amount = Milliliters(20.0)

        // When
        val after = tank.consume(amount)

        // Then
        after.current.value shouldBe 0.0
    }

    @Test
    fun `consume beyond current throws with precise message`() {
        // Given
        val tank =
            WaterTank(
                capacity = Milliliters(100.0),
                current = Milliliters(30.0),
            )
        val amount = Milliliters(40.0)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                tank.consume(amount)
            }

        // Then
        ex.message shouldBe "Volume cannot be negative"
    }

    @Test
    fun `refill sets current to capacity`() {
        // Given
        val tank =
            WaterTank(
                capacity = Milliliters(200.0),
                current = Milliliters(25.0),
            )

        // When
        val after = tank.refill()

        // Then
        after.current.value shouldBe 200.0
        tank.current.value shouldBe 25.0
    }
}

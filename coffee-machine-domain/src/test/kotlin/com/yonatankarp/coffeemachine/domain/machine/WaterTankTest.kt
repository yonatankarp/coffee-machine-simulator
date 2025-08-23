package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WaterTankTest {
    @Test
    fun `cannot construct tank with current greater than capacity`() {
        // Given
        val capacity = MillilitersFixture.fiveHundred
        val current = MillilitersFixture.oneThousand

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
        val tank = WaterTankFixture.used
        val amount = MillilitersFixture.ten

        // When
        val after = tank.consume(amount)

        // Then
        after.current shouldBe MillilitersFixture.fourHundredNinety
        tank.current shouldBe MillilitersFixture.fiveHundred
    }

    @Test
    fun `consume can bring current to exactly zero`() {
        // Given
        val tank = WaterTankFixture.full
        val amount = MillilitersFixture.oneThousand

        // When
        val after = tank.consume(amount)

        // Then
        after.current shouldBe Milliliters.ZERO
    }

    @Test
    fun `consume beyond current throws with precise message`() {
        // Given
        val tank = WaterTankFixture.used
        val amount = MillilitersFixture.sixHundred

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
        val tank = WaterTankFixture.used

        // When
        val after = tank.refill()

        // Then
        after.current shouldBe MillilitersFixture.oneThousand
        tank.current shouldBe MillilitersFixture.fiveHundred
    }
}

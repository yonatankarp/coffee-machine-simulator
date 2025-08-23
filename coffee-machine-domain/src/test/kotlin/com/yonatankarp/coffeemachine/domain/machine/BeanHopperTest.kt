package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BeanHopperTest {
    @Test
    fun `cannot construct hopper with current greater than capacity`() {
        // Given
        val capacity = GramsFixture.fiveHundred
        val current = GramsFixture.sixHundred

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                BeanHopper(capacity = capacity, current = current)
            }

        // Then
        ex.message shouldBe "Beans overflow"
    }

    @Test
    fun `consume reduces current by the requested amount`() {
        // Given
        val hopper = BeanHopperFixture.used
        val amount = GramsFixture.twenty

        // When
        val after = hopper.consume(amount)

        // Then
        after.current shouldBe GramsFixture.twoHundredEighty
        hopper.current shouldBe GramsFixture.threeHundred
    }

    @Test
    fun `consume can bring current to exactly zero`() {
        // Given
        val hopper = BeanHopperFixture.used
        val amount = GramsFixture.threeHundred

        // When
        val after = hopper.consume(amount)

        // Then
        after.current shouldBe Grams.ZERO
    }

    @Test
    fun `consume beyond current throws with precise message`() {
        // Given
        val hopper = BeanHopperFixture.used
        val amount = GramsFixture.fiveHundred

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                hopper.consume(amount)
            }

        // Then
        ex.message shouldBe "Mass cannot be negative"
    }

    @Test
    fun `refill sets current to capacity`() {
        // Given
        val hopper = BeanHopperFixture.used

        // When
        val after = hopper.refill()

        // Then
        after.current shouldBe GramsFixture.fiveHundred
        hopper.current shouldBe GramsFixture.threeHundred
    }
}

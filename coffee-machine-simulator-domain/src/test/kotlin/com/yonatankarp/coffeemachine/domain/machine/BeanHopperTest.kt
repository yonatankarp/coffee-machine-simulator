package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BeanHopperTest {
    @Test
    fun `cannot construct hopper with current greater than capacity`() {
        // Given
        val capacity = Grams(500.0)
        val current = Grams(600.0)

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
        val hopper = BeanHopper(capacity = Grams(500.0), current = Grams(120.0))
        val amount = Grams(20.0)

        // When
        val after = hopper.consume(amount)

        // Then
        after.current.value shouldBe 100.0
        hopper.current.value shouldBe 120.0
    }

    @Test
    fun `consume can bring current to exactly zero`() {
        // Given
        val hopper = BeanHopper(capacity = Grams(500.0), current = Grams(15.0))
        val amount = Grams(15.0)

        // When
        val after = hopper.consume(amount)

        // Then
        after.current.value shouldBe 0.0
    }

    @Test
    fun `consume beyond current throws with precise message`() {
        // Given
        val hopper = BeanHopper(capacity = Grams(500.0), current = Grams(12.0))
        val amount = Grams(20.0)

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
        val hopper = BeanHopper(capacity = Grams(500.0), current = Grams(25.0))

        // When
        val after = hopper.refill()

        // Then
        after.current.value shouldBe 500.0
        hopper.current.value shouldBe 25.0
    }
}

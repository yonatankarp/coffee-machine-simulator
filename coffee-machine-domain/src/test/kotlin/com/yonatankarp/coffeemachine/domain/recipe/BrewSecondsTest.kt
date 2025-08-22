package com.yonatankarp.coffeemachine.domain.recipe

import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BrewSecondsTest {
    @Test
    fun `accepts lower bound 1s`() {
        // Given
        val seconds = Seconds(1.0)

        // When
        val brew = BrewSeconds(seconds)

        // Then
        brew.second shouldBe seconds
    }

    @Test
    fun `accepts upper bound 120s`() {
        // Given
        val seconds = Seconds(120.0)

        // When
        val brew = BrewSeconds(seconds)

        // Then
        brew.second shouldBe seconds
    }

    @Test
    fun `rejects values below 1s`() {
        // Given
        val seconds = Seconds(0.0)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                BrewSeconds(seconds)
            }

        // Then
        ex.message shouldBe "Brew must be between 1 and 120 seconds"
    }

    @Test
    fun `rejects values above 120s`() {
        // Given
        val s = Seconds(121.0)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                BrewSeconds(s)
            }

        // Then
        ex.message shouldBe "Brew must be between 1 and 120 seconds"
    }
}

package com.yonatankarp.coffeemachine.domain.machine

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WasteBinTest {
    @Test
    fun `constructing a valid bin succeeds`() {
        // Given
        val capacity = 5
        val current = 2

        // When
        val bin = WasteBin(capacityPucks = capacity, currentPucks = current)

        // Then
        bin.capacityPucks shouldBe 5
        bin.currentPucks shouldBe 2
    }

    @Test
    fun `capacity must be strictly positive - zero rejected`() {
        // Given
        val capacity = 0
        val current = 0

        // When // Then
        shouldThrow<IllegalArgumentException> {
            WasteBin(capacityPucks = capacity, currentPucks = current)
        }
    }

    @Test
    fun `capacity must be strictly positive - negative rejected`() {
        // Given
        val capacity = -1
        val current = 0

        // When // Then
        shouldThrow<IllegalArgumentException> {
            WasteBin(capacityPucks = capacity, currentPucks = current)
        }
    }

    @Test
    fun `current must be within 0-capacity - below zero rejected`() {
        // Given
        val capacity = 3
        val current = -1

        // When // Then
        shouldThrow<IllegalArgumentException> {
            WasteBin(capacityPucks = capacity, currentPucks = current)
        }
    }

    @Test
    fun `current must be within 0-capacity - above capacity rejected`() {
        // Given
        val capacity = 3
        val current = 4

        // When // Then
        shouldThrow<IllegalArgumentException> {
            WasteBin(capacityPucks = capacity, currentPucks = current)
        }
    }

    @Test
    fun `addPuck increments current by one`() {
        // Given
        val bin = WasteBin(capacityPucks = 3, currentPucks = 1)

        // When
        val after = bin.addPuck()

        // Then
        after.currentPucks shouldBe 2
        bin.currentPucks shouldBe 1
    }

    @Test
    fun `addPuck throws when bin is full`() {
        // Given
        val bin = WasteBin(capacityPucks = 2, currentPucks = 2)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                bin.addPuck()
            }

        // Then
        ex.message shouldBe "Waste bin full"
    }

    @Test
    fun `empty resets current to zero`() {
        // Given
        val bin = WasteBin(capacityPucks = 4, currentPucks = 3)

        // When
        val after = bin.empty()

        // Then
        after.currentPucks shouldBe 0
        bin.currentPucks shouldBe 3
    }
}

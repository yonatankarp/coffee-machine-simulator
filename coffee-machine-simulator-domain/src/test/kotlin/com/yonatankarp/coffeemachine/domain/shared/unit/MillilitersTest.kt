package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class MillilitersTest {
    @Test
    fun `volume formatted as integer ml`() {
        val v = Milliliters(59.6)
        assertEquals("60 ml", v.toString())
    }

    @Test
    fun `reject negative volume`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Milliliters(-1.0)
        }
        assertEquals("Volume cannot be negative", ex.message)
    }

    @Test
    fun `zero ml is allowed`() {
        Milliliters(0.0)
    }
}

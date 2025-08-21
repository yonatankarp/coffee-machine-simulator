package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class BarTest {
    @Test
    fun `non-negative pressure allowed and uses two decimals`() {
        val p = Bar(9.876)
        assertEquals("9.88 bar", p.toString())
    }

    @Test
    fun `reject negative pressure`() {
        val ex =
            assertFailsWith<IllegalArgumentException> {
                Bar(-0.0001)
            }
        assertEquals("Pressure cannot be negative", ex.message)
    }

    @Test
    fun `zero pressure is allowed`() {
        Bar(0.0) // no exception
    }
}

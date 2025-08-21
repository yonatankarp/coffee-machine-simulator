package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SecondsTest {
    @Test
    fun `string uses one decimal and s suffix`() {
        val s = Seconds(3.14159)
        assertEquals("3.1 s", s.toString())
    }

    @Test
    fun `ZERO constant is exactly 0_0 seconds`() {
        assertEquals(Seconds(0.0).toString(), Seconds.ZERO.toString())
    }

    @Test
    fun `fromMillis converts non-negative millis`() {
        val s = Seconds.fromMillis(1500L)
        assertEquals(Seconds(1.5).toString(), s.toString())
    }

    @Test
    fun `reject negative seconds`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Seconds(-0.0001)
        }
        assertEquals("Time cannot be negative", ex.message)
    }

    @Test
    fun `fromMillis rejects negative durations`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Seconds.fromMillis(-1L)
        }
        assertEquals("Duration cannot be negative", ex.message)
    }
}

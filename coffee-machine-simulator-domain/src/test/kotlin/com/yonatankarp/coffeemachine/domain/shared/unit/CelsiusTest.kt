package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CelsiusTest {
    @Test
    fun `valid temperature formats with one decimal and degree symbol`() {
        val temperature = Celsius(93.456)
        assertEquals("93.5°C", temperature.toString())
    }

    @Test
    fun `reject temperature below absolute zero`() {
        val ex =
            assertFailsWith<IllegalArgumentException> {
                Celsius(-273.151)
            }
        assertEquals("Temperature below absolute zero", ex.message)
    }

    @Test
    fun `borderline above absolute zero is accepted`() {
        Celsius(-273.149)
    }
}

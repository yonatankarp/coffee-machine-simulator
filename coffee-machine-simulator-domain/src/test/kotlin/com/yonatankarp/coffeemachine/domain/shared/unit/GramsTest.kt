package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GramsTest {
    @Test
    fun `string uses one decimal and g suffix`() {
        val g = Grams(18.04)
        assertEquals("18.0 g", g.toString())
    }

    @Test
    fun `sum of grams adds values`() {
        val a = Grams(7.5)
        val b = Grams(2.4)
        assertEquals(Grams(9.9).toString(), (a + b).toString())
    }

    @Test
    fun `minus saturates at zero`() {
        val a = Grams(7.0)
        val b = Grams(8.0)
        val c = a - b
        assertEquals(Grams(0.0).toString(), c.toString())
    }

    @Test
    fun `reject negative mass`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Grams(-0.001)
        }
        assertEquals("Mass cannot be negative", ex.message)
    }
}

package com.yonatankarp.coffeemachine.domain.shared.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class MlPerSecTest {
    @Test
    fun `non-negative flow allowed and uses two decimals`() {
        val f = MlPerSec(1.234)
        assertEquals("1.23 ml/s", f.toString())
    }

    @Test
    fun `reject negative flow`() {
        val ex =
            assertFailsWith<IllegalArgumentException> {
                MlPerSec(-0.01)
            }
        assertEquals("Flow cannot be negative", ex.message)
    }

    @Test
    fun `zero flow is allowed`() {
        MlPerSec(0.0)
    }
}

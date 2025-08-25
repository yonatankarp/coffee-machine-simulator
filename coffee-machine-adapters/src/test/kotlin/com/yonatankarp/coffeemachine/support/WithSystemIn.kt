package com.yonatankarp.coffeemachine.support

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

/**
 * Temporarily sets System.in to the provided text (joined with newlines).
 * Restores the original stream on close.
 */
class WithSystemIn(
    vararg lines: String,
) : AutoCloseable {
    private val original = System.`in`

    init {
        val text = lines.joinToString(separator = "\n") + "\n"
        System.setIn(ByteArrayInputStream(text.toByteArray(StandardCharsets.UTF_8)))
    }

    override fun close() {
        System.setIn(original)
    }
}

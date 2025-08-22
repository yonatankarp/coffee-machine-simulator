package com.yonatankarp.coffeemachine.domain.shared.extension

import com.yonatankarp.coffeemachine.domain.shared.extension.FormatExtensions.format
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.Locale

class FormatExtensionsTest {
    private lateinit var previousDefault: Locale

    @BeforeEach
    fun forceCommaLocale() {
        // Save and switch to a locale that normally uses commas for decimals
        previousDefault = Locale.getDefault()
        Locale.setDefault(Locale.GERMANY)
    }

    @AfterEach
    fun restoreLocale() {
        Locale.setDefault(previousDefault)
    }

    @ParameterizedTest(name = "value={0}, pattern=\"{1}\" -> \"{2}\"")
    @CsvSource(
        "93.5,  %.1f°C,  93.5°C",
        "9.876, %.2f bar, 9.88 bar",
        "0.0,   %.0f ml,  0 ml",
        "12.3456, %.3f,   12.346",
        "1.0,   %.2f ml/s, 1.00 ml/s",
        "1000.0, %.1f g,  1000.0 g",
    )
    fun `formats using Locale-ROOT regardless of JVM default`(
        value: Double,
        pattern: String,
        expected: String,
    ) {
        val actual = value.format(pattern)
        actual shouldBe expected
    }
}

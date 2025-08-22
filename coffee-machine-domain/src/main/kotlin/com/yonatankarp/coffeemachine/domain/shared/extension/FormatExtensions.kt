package com.yonatankarp.coffeemachine.domain.shared.extension

import java.util.Locale

object FormatExtensions {
    fun Double.format(pattern: String): String = String.format(Locale.ROOT, pattern, this)
}

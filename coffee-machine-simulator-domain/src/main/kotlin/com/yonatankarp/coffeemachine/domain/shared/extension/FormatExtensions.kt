package com.yonatankarp.coffeemachine.domain.shared.extension

import java.util.Locale

internal fun Double.format(pattern: String): String =
    String.format(Locale.ROOT, pattern, this)

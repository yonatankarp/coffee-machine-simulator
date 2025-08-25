package com.yonatankarp.coffeemachine.support

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

private val classLoader: ClassLoader
    get() = Thread.currentThread().contextClassLoader

fun readFileAsText(
    path: String,
    charset: Charset = StandardCharsets.UTF_8,
): String =
    classLoader
        .getResourceAsStream(path)
        ?.bufferedReader(charset)
        ?.use { it.readText() }
        ?: error("Test resource not found on classpath: $path")

fun readFileAsLines(
    path: String,
    charset: Charset = StandardCharsets.UTF_8,
): Array<String> =
    readFileAsText(path, charset)
        .lines()
        .filter { it.isNotBlank() }
        .toTypedArray()

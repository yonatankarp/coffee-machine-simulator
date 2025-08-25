package com.yonatankarp.coffeemachine.support

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.slf4j.LoggerFactory

/**
 * Captures Logback events for a given SLF4J logger name.
 * Use with KotlinLogging by passing the class' name.
 */
class LogCapture(
    val logger: Logger,
    level: Level = Level.INFO,
) : AutoCloseable {
    constructor(loggerClass: Class<*>, level: Level = Level.INFO) :
        this(
            LoggerFactory.getLogger(loggerClass) as Logger,
            level,
        )

    constructor(loggerName: String, level: Level = Level.INFO) :
        this(
            LoggerFactory.getLogger(loggerName) as Logger,
            level,
        )

    private val previousLevel: Level? = logger.level
    private val previousAdditive: Boolean = logger.isAdditive
    private val appender = ListAppender<ILoggingEvent>()

    init {
        logger.level = level
        logger.isAdditive = false // isolate from root appends
        appender.start()
        logger.addAppender(appender)
    }

    val events: List<ILoggingEvent>
        get() = appender.list.toList()

    val messages: List<String>
        get() = events.map { it.formattedMessage.normalizeLog() }

    override fun close() {
        logger.detachAppender(appender)
        appender.stop()
        logger.level = previousLevel
        logger.isAdditive = previousAdditive
    }

    private fun String.normalizeLog(): String =
        replace(Regex("\u001B\\[[;\\d]*m"), "") // strip ANSI colors if any
            .replace("\r\n", "\n")
            .trimEnd()

    companion object {
        fun root(level: Level = Level.INFO): LogCapture = LogCapture(Logger.ROOT_LOGGER_NAME, level)
    }
}

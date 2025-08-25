package com.yonatankarp.coffeemachine.adapters

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

@Configuration
class WebAdaptersConfiguration {
    @Bean
    fun scheduledExecutor(): ScheduledExecutorService = Executors.newScheduledThreadPool(2)
}

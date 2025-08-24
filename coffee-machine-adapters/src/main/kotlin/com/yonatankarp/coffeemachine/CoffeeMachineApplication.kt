package com.yonatankarp.coffeemachine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoffeeMachineApplication

fun main(args: Array<String>) {
    runApplication<CoffeeMachineApplication>(*args)
}

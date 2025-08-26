package com.yonatankarp.coffeemachine.domain.machine.exception

sealed class CoffeeMachineException(
    message: String,
) : Exception(message) {
    class NoCoffeeMachineFound : CoffeeMachineException("No coffee machine found")
}

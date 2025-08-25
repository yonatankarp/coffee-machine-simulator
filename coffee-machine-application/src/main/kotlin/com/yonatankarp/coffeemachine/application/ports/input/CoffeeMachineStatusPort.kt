package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.MachineStatus

fun interface CoffeeMachineStatusPort {
    operator fun invoke(): MachineStatus
}

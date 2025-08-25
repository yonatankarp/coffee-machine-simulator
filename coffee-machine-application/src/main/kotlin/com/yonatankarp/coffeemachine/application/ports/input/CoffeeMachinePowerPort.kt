package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.MachineStatus

fun interface CoffeeMachinePowerPort {
    operator fun invoke(on: Boolean): MachineStatus
}

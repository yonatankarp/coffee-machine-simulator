package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

fun interface CoffeeMachinePowerPort {
    operator fun invoke(on: Boolean): MachineStatus
}

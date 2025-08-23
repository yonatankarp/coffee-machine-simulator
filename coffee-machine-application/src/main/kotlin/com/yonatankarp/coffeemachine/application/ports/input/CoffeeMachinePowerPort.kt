package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus

fun interface CoffeeMachinePowerPort {
    operator fun invoke(on: Boolean): MachineStatus
}

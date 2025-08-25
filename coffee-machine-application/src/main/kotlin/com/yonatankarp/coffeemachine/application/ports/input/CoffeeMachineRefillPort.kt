package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.RefillType

fun interface CoffeeMachineRefillPort {
    operator fun invoke(type: RefillType): MachineStatus
}

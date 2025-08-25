package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

fun interface RefillMachine {
    operator fun invoke(type: RefillType): MachineStatus
}

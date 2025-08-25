package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

fun interface GetMachineStatus {
    operator fun invoke(): MachineStatus
}

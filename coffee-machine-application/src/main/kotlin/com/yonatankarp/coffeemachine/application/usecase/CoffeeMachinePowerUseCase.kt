package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.ManageMachine
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

class CoffeeMachinePowerUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : ManageMachine {
    override operator fun invoke(on: Boolean): MachineStatus {
        val machine = coffeeMachineRepository.load()
        val updated = if (on) machine.powerOn() else machine.powerOff()
        coffeeMachineRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachinePowerPort
import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.port.MachineStateRepository

class CoffeeMachinePowerUseCase(
    private val stateRepository: MachineStateRepository,
) : CoffeeMachinePowerPort {
    override operator fun invoke(on: Boolean): MachineStatus {
        val machine = stateRepository.load()
        val updated = if (on) machine.powerOn() else machine.powerOff()
        stateRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

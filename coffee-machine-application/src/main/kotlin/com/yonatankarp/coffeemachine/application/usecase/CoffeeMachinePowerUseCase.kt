package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachinePowerPort
import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository

class CoffeeMachinePowerUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : CoffeeMachinePowerPort {
    override operator fun invoke(on: Boolean): MachineStatus {
        val machine = coffeeMachineRepository.load()
        val updated = if (on) machine.powerOn() else machine.powerOff()
        coffeeMachineRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineRefillPort
import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.machine.port.MachineStateRepository

class RefillUseCase(
    private val stateRepository: MachineStateRepository,
) : CoffeeMachineRefillPort {
    override fun invoke(type: RefillType): MachineStatus {
        val machine = stateRepository.load()
        val updated = machine.refill(type)
        stateRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

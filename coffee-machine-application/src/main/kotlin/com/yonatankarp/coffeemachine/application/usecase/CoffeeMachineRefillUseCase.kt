package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.RefillMachine
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

class CoffeeMachineRefillUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : RefillMachine {
    override fun invoke(type: RefillType): MachineStatus {
        val machine = coffeeMachineRepository.load()
        val updated = machine.refill(type)
        coffeeMachineRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

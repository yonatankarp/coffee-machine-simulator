package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineRefillPort
import com.yonatankarp.coffeemachine.domain.machine.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository

class CoffeeMachineRefillUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : CoffeeMachineRefillPort {
    override fun invoke(type: RefillType): MachineStatus {
        val machine = coffeeMachineRepository.load()
        val updated = machine.refill(type)
        coffeeMachineRepository.save(updated)
        return MachineStatus.from(updated)
    }
}

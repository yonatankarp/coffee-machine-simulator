package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineStatusPort
import com.yonatankarp.coffeemachine.domain.machine.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository

class CoffeeMachineStatusUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : CoffeeMachineStatusPort {
    override fun invoke() = MachineStatus.from(coffeeMachineRepository.load())
}

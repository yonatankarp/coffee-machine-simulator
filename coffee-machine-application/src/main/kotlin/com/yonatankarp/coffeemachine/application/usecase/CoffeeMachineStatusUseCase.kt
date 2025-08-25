package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.GetMachineStatus
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus

class CoffeeMachineStatusUseCase(
    private val coffeeMachineRepository: CoffeeMachineRepository,
) : GetMachineStatus {
    override fun invoke() = MachineStatus.from(coffeeMachineRepository.load())
}

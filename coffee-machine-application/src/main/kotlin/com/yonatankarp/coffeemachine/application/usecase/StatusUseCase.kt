package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.ports.input.CoffeeMachineStatusPort
import com.yonatankarp.coffeemachine.application.usecase.model.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.port.MachineStateRepository

class StatusUseCase(
    private val stateRepository: MachineStateRepository,
) : CoffeeMachineStatusPort {
    override fun invoke() = MachineStatus.from(stateRepository.load())
}

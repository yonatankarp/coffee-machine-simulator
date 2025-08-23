package com.yonatankarp.coffeemachine.application.fakes

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.port.MachineStateRepository
import java.util.concurrent.atomic.AtomicReference

class FakeMachineRepository(
    initial: CoffeeMachine,
) : MachineStateRepository {
    private val ref = AtomicReference(initial)

    override fun load(): CoffeeMachine = ref.get()

    override fun save(machine: CoffeeMachine): CoffeeMachine {
        ref.set(machine)
        return machine
    }
}

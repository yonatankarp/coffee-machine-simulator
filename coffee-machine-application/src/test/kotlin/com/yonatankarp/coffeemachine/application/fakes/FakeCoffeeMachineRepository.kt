package com.yonatankarp.coffeemachine.application.fakes

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import java.util.concurrent.atomic.AtomicReference

class FakeCoffeeMachineRepository(
    initial: CoffeeMachine,
) : CoffeeMachineRepository {
    private val ref = AtomicReference(initial)

    override fun load(): CoffeeMachine = ref.get()

    override fun save(machine: CoffeeMachine): CoffeeMachine {
        ref.set(machine)
        return machine
    }
}

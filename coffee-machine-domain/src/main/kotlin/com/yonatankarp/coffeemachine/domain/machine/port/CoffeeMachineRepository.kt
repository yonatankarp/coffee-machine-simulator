package com.yonatankarp.coffeemachine.domain.machine.port

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine

interface CoffeeMachineRepository {
    fun load(): CoffeeMachine

    fun save(machine: CoffeeMachine): CoffeeMachine
}

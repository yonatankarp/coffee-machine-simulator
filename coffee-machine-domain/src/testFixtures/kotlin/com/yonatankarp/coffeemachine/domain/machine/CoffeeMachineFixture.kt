package com.yonatankarp.coffeemachine.domain.machine

object CoffeeMachineFixture {
    val defaultModel = CoffeeMachine.Model("KotlinBarista 3000")

    val poweredMachine = defaultMachine(powered = true)

    val unpoweredMachine = defaultMachine(powered = false)

    fun defaultMachine(
        model: CoffeeMachine.Model = defaultModel,
        waterTank: WaterTank = WaterTankFixture.used,
        beanHopper: BeanHopper = BeanHopperFixture.used,
        wasteBin: WasteBin = WasteBinFixture.used,
        powered: Boolean = true,
    ) = CoffeeMachine(
        model = model,
        waterTank = waterTank,
        beanHopper = beanHopper,
        wasteBin = wasteBin,
        poweredOn = powered,
    )
}

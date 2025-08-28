package com.yonatankarp.coffeemachine.domain.machine

object CoffeeMachineFixture {
    val defaultModel get() = CoffeeMachine.Model("KotlinBarista 3000")

    val poweredMachine get() = defaultMachine(powered = true)

    val unpoweredMachine get() = defaultMachine(powered = false)

    fun defaultMachine(
        model: CoffeeMachine.Model = defaultModel,
        waterTank: WaterTank = WaterTankFixture.used,
        beanHopper: BeanHopper = BeanHopperFixture.used,
        wasteBin: WasteBin = WasteBinFixture.used,
        powered: Boolean = true,
    ) = CoffeeMachine(
        id = CoffeeMachine.Id.default(),
        version = 1,
        model = model,
        waterTank = waterTank,
        beanHopper = beanHopper,
        wasteBin = wasteBin,
        poweredOn = powered,
    )
}

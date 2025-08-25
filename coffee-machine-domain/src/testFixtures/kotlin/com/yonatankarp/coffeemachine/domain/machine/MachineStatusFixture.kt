package com.yonatankarp.coffeemachine.domain.machine

object MachineStatusFixture {
    val poweredOff: MachineStatus
        get() =
            machineStatus(
                poweredOn = false,
                water = WaterStatusFixture.empty,
                beans = BeanStatusFixture.empty,
                waste = WasteStatusFixture.empty,
            )

    val poweredOn: MachineStatus
        get() =
            machineStatus(
                poweredOn = true,
                water = WaterStatusFixture.full,
                beans = BeanStatusFixture.full,
                waste = WasteStatusFixture.empty,
            )

    val used: MachineStatus
        get() =
            machineStatus(
                poweredOn = true,
                water = WaterStatusFixture.used,
                beans = BeanStatusFixture.used,
                waste = WasteStatusFixture.used,
            )

    private fun machineStatus(
        poweredOn: Boolean,
        water: WaterStatus,
        beans: BeansStatus,
        waste: WasteStatus,
    ) = MachineStatus(
        model = CoffeeMachine.Model("KotlinBarista 3000"),
        poweredOn = poweredOn,
        water = water,
        beans = beans,
        waste = waste,
    )
}

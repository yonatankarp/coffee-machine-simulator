package com.yonatankarp.coffeemachine.domain.machine.status

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine

data class MachineStatus(
    val model: CoffeeMachine.Model,
    val poweredOn: Boolean,
    val water: WaterStatus,
    val beans: BeansStatus,
    val waste: WasteStatus,
    val isBrewing: Boolean,
) {
    companion object {
        fun from(machine: CoffeeMachine) =
            MachineStatus(
                model = machine.model,
                poweredOn = machine.poweredOn,
                isBrewing = machine.isBrewing,
                water = machine.toWaterStatus(),
                beans = machine.toBeanStatus(),
                waste = machine.toWasteStatus(),
            )

        private fun CoffeeMachine.toWaterStatus() =
            WaterStatus(
                current = waterTank.current,
                capacity = waterTank.capacity,
            )

        private fun CoffeeMachine.toBeanStatus() =
            BeansStatus(
                current = beanHopper.current,
                capacity = beanHopper.capacity,
            )

        private fun CoffeeMachine.toWasteStatus() =
            WasteStatus(
                currentPucks = wasteBin.currentPucks,
                capacityPucks = wasteBin.capacityPucks,
            )
    }
}

package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.CoffeeMachineEntity
import com.yonatankarp.coffeemachine.domain.machine.BeanHopper
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineId
import com.yonatankarp.coffeemachine.domain.machine.WasteBin
import com.yonatankarp.coffeemachine.domain.machine.WaterTank
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters

object CoffeeMachineMapper {
    fun CoffeeMachineEntity.toDomain() =
        CoffeeMachine(
            id = CoffeeMachineId(id),
            version = version,
            model = CoffeeMachine.Model(model),
            waterTank = toWaterTank(),
            beanHopper = toBeanHopper(),
            wasteBin = toWasteBin(),
            poweredOn = poweredOn,
        )

    private fun CoffeeMachineEntity.toWaterTank() =
        WaterTank(
            capacity = Milliliters(waterCapacity),
            current = Milliliters(waterCurrent),
        )

    private fun CoffeeMachineEntity.toBeanHopper() =
        BeanHopper(
            capacity = Grams(beansCapacity),
            current = Grams(beansCurrent),
        )

    private fun CoffeeMachineEntity.toWasteBin() =
        WasteBin(
            capacityPucks = wasteCapacityPucks,
            currentPucks = wasteCurrentPucks,
        )

    fun CoffeeMachine.toEntity(): CoffeeMachineEntity =
        CoffeeMachineEntity(
            id = id.value,
            version = version,
            model = model.value,
            waterCapacity = waterTank.capacity.value,
            waterCurrent = waterTank.current.value,
            beansCapacity = beanHopper.capacity.value,
            beansCurrent = beanHopper.current.value,
            wasteCapacityPucks = wasteBin.capacityPucks,
            wasteCurrentPucks = wasteBin.currentPucks,
            poweredOn = poweredOn,
        )
}

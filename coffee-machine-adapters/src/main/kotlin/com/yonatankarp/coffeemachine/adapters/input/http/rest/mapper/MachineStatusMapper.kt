package com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper

import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus
import com.yonatankarp.coffeemachine.openapi.model.MachineStatus as MachineStatusApi

object MachineStatusMapper {
    fun MachineStatus.toApi() =
        MachineStatusApi(
            model = model.value,
            poweredOn = poweredOn,
            waterCurrent = water.current.value.toInt(),
            waterCapacity = water.capacity.value.toInt(),
            beansCurrent = beans.current.value.toInt(),
            beansCapacity = beans.capacity.value.toInt(),
            wasteCurrentPucks = waste.currentPucks,
            wasteCapacityPucks = waste.capacityPucks,
            brewing = isBrewing,
            brewElapsedSeconds = 0, // TODO :remove?
            brewTotalSeconds = 0, // TODO :remove?
        )
}

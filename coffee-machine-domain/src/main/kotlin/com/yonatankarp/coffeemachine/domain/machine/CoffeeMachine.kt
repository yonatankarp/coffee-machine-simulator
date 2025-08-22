package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.recipe.Recipe

data class CoffeeMachine(
    val model: Model,
    val waterTank: WaterTank,
    val beanHopper: BeanHopper,
    val wasteBin: WasteBin,
    val poweredOn: Boolean = false,
) {
    fun powerOn() = copy(poweredOn = true)

    fun powerOff() = copy(poweredOn = false)

    fun withTanks(
        waterTank: WaterTank = this.waterTank,
        beanHopper: BeanHopper = this.beanHopper,
        wasteBin: WasteBin = this.wasteBin,
    ) = copy(
        waterTank = waterTank,
        beanHopper = beanHopper,
        wasteBin = wasteBin,
    )

    fun brew(recipe: Recipe): Outcome {
        require(poweredOn) { "Machine is OFF" }

        val events = mutableListOf<DomainEvent>()
        events += DomainEvent.HeatingRequested(target = recipe.temperature)
        events += DomainEvent.GrindingRequested(amount = recipe.beans)
        events +=
            DomainEvent.BrewingRequested(
                recipe = recipe.name,
                water = recipe.water,
                duration = recipe.brewSeconds,
            )

        val newWater = waterTank.consume(amount = recipe.water)
        val newBeans = beanHopper.consume(amount = recipe.beans)
        val newWaste = wasteBin.addPuck()

        events += DomainEvent.ResourcesConsumed(recipe.water, recipe.beans)
        events += DomainEvent.WastePuckAdded(newWaste.currentPucks)
        events += DomainEvent.BrewCompleted(recipe.name)

        val updated =
            withTanks(
                waterTank = newWater,
                beanHopper = newBeans,
                wasteBin = newWaste,
            )
        return Outcome(updatedMachine = updated, events = events.toList())
    }

    fun refill(type: RefillType): CoffeeMachine =
        when (type) {
            RefillType.WATER -> withTanks(waterTank = waterTank.refill())
            RefillType.BEANS -> withTanks(beanHopper = beanHopper.refill())
            RefillType.WASTE -> withTanks(wasteBin = wasteBin.empty())
        }

    @JvmInline
    value class Model(
        val value: String,
    )

    data class Outcome(
        val updatedMachine: CoffeeMachine,
        val events: List<DomainEvent>,
    )
}

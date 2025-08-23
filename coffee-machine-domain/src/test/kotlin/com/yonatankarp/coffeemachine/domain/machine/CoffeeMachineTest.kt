package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEventFixture
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture.espresso
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoffeeMachineTest {
    @Test
    fun `powerOn sets poweredOn true and powerOff sets false`() {
        // Given
        val off = CoffeeMachineFixture.unpoweredMachine

        // When
        val on = off.powerOn()
        val backOff = on.powerOff()

        // Then
        on.poweredOn shouldBe true
        backOff.poweredOn shouldBe false
        off.poweredOn shouldBe false
    }

    @Test
    fun `with replaces only provided tanks and keeps others`() {
        // Given
        val machine = CoffeeMachineFixture.poweredMachine
        val newWater = WaterTankFixture.full

        // When
        val updatedMachine = machine.with(waterTank = newWater)

        // Then
        updatedMachine.waterTank shouldBe newWater
        updatedMachine.beanHopper shouldBe machine.beanHopper
        updatedMachine.wasteBin shouldBe machine.wasteBin
        machine.waterTank.current shouldBe MillilitersFixture.fiveHundred
    }

    @Test
    fun `brew emits ordered events and consumes resources`() {
        // Given
        val machine = CoffeeMachineFixture.poweredMachine
        val recipe = espresso

        // When
        val outcome = machine.brew(recipe)

        // Then
        outcome.updatedMachine.waterTank.current shouldBe Milliliters(470.00)
        outcome.updatedMachine.beanHopper.current shouldBe Grams(291.0)
        outcome.updatedMachine.wasteBin.currentPucks shouldBe 4

        outcome.events.map { it::class } shouldContainExactly DomainEventFixture.eventClasses

        (outcome.events[0] as DomainEvent.HeatingRequested).target shouldBe recipe.temperature
        (outcome.events[1] as DomainEvent.GrindingRequested).amount shouldBe recipe.beans
        (outcome.events[2] as DomainEvent.BrewingRequested).let {
            it.recipe shouldBe recipe.name
            it.water shouldBe recipe.water
            it.duration shouldBe recipe.brewSeconds
        }
        (outcome.events[3] as DomainEvent.ResourcesConsumed).let {
            it.water shouldBe recipe.water
            it.beans shouldBe recipe.beans
        }
        (outcome.events[5] as DomainEvent.BrewCompleted).recipe shouldBe recipe.name
    }

    @Test
    fun `brew requires powered on machine`() {
        // Given
        val machine = CoffeeMachineFixture.unpoweredMachine
        val recipe = espresso

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                machine.brew(recipe)
            }

        // Then
        ex.message shouldBe "Machine is OFF"
    }

    @Test
    fun `refill handles all RefillType variants`() {
        // Given
        val start = CoffeeMachineFixture.poweredMachine

        // When
        val afterWater = start.refill(RefillType.WATER)
        val afterBeans = afterWater.refill(RefillType.BEANS)
        val afterWaste = afterBeans.refill(RefillType.WASTE)

        // Then
        afterWater.waterTank.current shouldBe MillilitersFixture.oneThousand
        afterBeans.beanHopper.current shouldBe GramsFixture.fiveHundred
        afterWaste.wasteBin.currentPucks shouldBe 0

        start.waterTank.current shouldBe MillilitersFixture.fiveHundred
        start.beanHopper.current shouldBe GramsFixture.threeHundred
        start.wasteBin.currentPucks shouldBe 3
    }
}

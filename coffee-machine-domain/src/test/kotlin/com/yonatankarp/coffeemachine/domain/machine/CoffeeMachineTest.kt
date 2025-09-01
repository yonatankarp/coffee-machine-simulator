package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.brew.BrewFixture
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEventFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Instant

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
        val initialBrew = BrewFixture.started
        val now = Instant.now()

        // When
        val outcome = machine.brew(initialBrew, now)

        // Then
        outcome.updatedMachine.waterTank.current shouldBe Milliliters(470.00)
        outcome.updatedMachine.beanHopper.current shouldBe Grams(282.0)
        outcome.updatedMachine.wasteBin.currentPucks shouldBe 4

        outcome.events.map { it::class } shouldContainExactly DomainEventFixture.eventClasses

        initialBrew.recipe
        (outcome.events[0] as DomainEvent.HeatingRequested).target shouldBe initialBrew.recipe.temperature
        (outcome.events[1] as DomainEvent.GrindingRequested).amount shouldBe initialBrew.recipe.beans
        (outcome.events[2] as DomainEvent.BrewingRequested).let {
            it.recipe shouldBe initialBrew.recipe.name
            it.water shouldBe initialBrew.recipe.water
            it.duration shouldBe initialBrew.recipe.brewSeconds
        }
        (outcome.events[3] as DomainEvent.ResourcesConsumed).let {
            it.water shouldBe initialBrew.recipe.water
            it.beans shouldBe initialBrew.recipe.beans
        }
        (outcome.events[5] as DomainEvent.BrewCompleted).recipe shouldBe initialBrew.recipe.name
    }

    @Test
    fun `brew requires powered on machine`() {
        // Given
        val machine = CoffeeMachineFixture.unpoweredMachine
        val initialBrew = BrewFixture.started
        val now = Instant.now()

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                machine.brew(initialBrew, now)
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

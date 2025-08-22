package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.unit.Celsius
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoffeeMachineTest {
    private fun machine(powered: Boolean = true) =
        CoffeeMachine(
            model = CoffeeMachine.Model("KotlinBarista 3000"),
            waterTank = WaterTank(capacity = Milliliters(200.0), current = Milliliters(120.0)),
            beanHopper = BeanHopper(capacity = Grams(100.0), current = Grams(50.0)),
            wasteBin = WasteBin(capacityPucks = 3, currentPucks = 1),
            poweredOn = powered,
        )

    private fun espresso() =
        Recipe(
            name = Recipe.Name("espresso"),
            water = Milliliters(30.0),
            beans = Grams(9.0),
            temperature = Celsius(93.0),
            grind = Recipe.GrindSize.FINE,
            brewSeconds = BrewSeconds(Seconds(28.0)),
        )

    @Test
    fun `powerOn sets poweredOn true and powerOff sets false`() {
        // Given
        val off = machine(powered = false)

        // When
        val on = off.powerOn()
        val backOff = on.powerOff()

        // Then
        on.poweredOn shouldBe true
        backOff.poweredOn shouldBe false
        off.poweredOn shouldBe false
    }

    @Test
    fun `withTanks replaces only provided tanks and keeps others`() {
        // Given
        val machine = machine()
        val newWater = WaterTank(capacity = Milliliters(300.0), current = Milliliters(150.0))

        // When
        val updatedMachine = machine.withTanks(waterTank = newWater)

        // Then
        updatedMachine.waterTank shouldBe newWater
        updatedMachine.beanHopper shouldBe machine.beanHopper
        updatedMachine.wasteBin shouldBe machine.wasteBin
        machine.waterTank.current.value shouldBe 120.0
    }

    @Test
    fun `brew emits ordered events and consumes resources`() {
        // Given
        val machine = machine(true)
        val recipe = espresso()

        // When
        val outcome = machine.brew(recipe)

        // Then
        outcome.updatedMachine.waterTank.current.value shouldBe 90.0
        outcome.updatedMachine.beanHopper.current.value shouldBe 41.0
        outcome.updatedMachine.wasteBin.currentPucks shouldBe 2

        outcome.events.map { it::class } shouldContainExactly
            listOf(
                DomainEvent.HeatingRequested::class,
                DomainEvent.GrindingRequested::class,
                DomainEvent.BrewingRequested::class,
                DomainEvent.ResourcesConsumed::class,
                DomainEvent.WastePuckAdded::class,
                DomainEvent.BrewCompleted::class,
            )

        (outcome.events[0] as DomainEvent.HeatingRequested).target.value shouldBe 93.0
        (outcome.events[1] as DomainEvent.GrindingRequested).amount.value shouldBe 9.0
        (outcome.events[2] as DomainEvent.BrewingRequested).let {
            it.recipe.value shouldBe "espresso"
            it.water.value shouldBe 30.0
            it.duration.second.value shouldBe 28.0
        }
        (outcome.events[3] as DomainEvent.ResourcesConsumed).let {
            it.water.value shouldBe 30.0
            it.beans.value shouldBe 9.0
        }
        (outcome.events[4] as DomainEvent.WastePuckAdded).puckCount shouldBe 2
        (outcome.events[5] as DomainEvent.BrewCompleted).recipe.value shouldBe "espresso"
    }

    @Test
    fun `brew requires powered on machine`() {
        // Given
        val machine = machine(powered = false)
        val recipe = espresso()

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
        val start =
            CoffeeMachine(
                model = CoffeeMachine.Model("KotlinBarista 3000"),
                waterTank = WaterTank(capacity = Milliliters(1000.0), current = Milliliters(10.0)),
                beanHopper = BeanHopper(capacity = Grams(500.0), current = Grams(5.0)),
                wasteBin = WasteBin(capacityPucks = 5, currentPucks = 4),
                poweredOn = true,
            )

        // When
        val afterWater = start.refill(RefillType.WATER)
        val afterBeans = afterWater.refill(RefillType.BEANS)
        val afterWaste = afterBeans.refill(RefillType.WASTE)

        // Then
        afterWater.waterTank.current.value shouldBe 1000.0
        afterBeans.beanHopper.current.value shouldBe 500.0
        afterWaste.wasteBin.currentPucks shouldBe 0

        start.waterTank.current.value shouldBe 10.0
        start.beanHopper.current.value shouldBe 5.0
        start.wasteBin.currentPucks shouldBe 4
    }
}

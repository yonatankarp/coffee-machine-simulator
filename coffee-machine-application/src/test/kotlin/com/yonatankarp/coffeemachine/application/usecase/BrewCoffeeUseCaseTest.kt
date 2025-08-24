package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.fakes.CollectingPublisher
import com.yonatankarp.coffeemachine.application.fakes.FakeCoffeeMachineRepository
import com.yonatankarp.coffeemachine.application.fakes.FakeRecipeRepository
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture.poweredMachine
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEventFixture
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture.espresso
import com.yonatankarp.coffeemachine.domain.shared.unit.GramsFixture.twoHundredNinetyOne
import com.yonatankarp.coffeemachine.domain.shared.unit.MillilitersFixture.fourHundredSeventy
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BrewCoffeeUseCaseTest {
    @Test
    fun `brewing publishes events and persists updated machine`() {
        // Given
        val machineRepository =
            FakeCoffeeMachineRepository(poweredMachine)
        val recipeRepository = FakeRecipeRepository(espresso)
        val publisher = CollectingPublisher()
        val brewCoffee =
            BrewCoffeeUseCase(machineRepository, recipeRepository, publisher)

        // When
        val events = brewCoffee(Recipe.Name.ESPRESSO)

        // Then
        events.map { it::class } shouldContainExactly DomainEventFixture.eventClasses

        publisher.published.size shouldBe 6
        publisher.published.map { it::class } shouldContainExactly
            events.map { it::class }

        val after = machineRepository.load()
        after.waterTank.current shouldBe fourHundredSeventy // 500 - 30
        after.beanHopper.current shouldBe twoHundredNinetyOne // 300 - 9
        after.wasteBin.currentPucks shouldBe 4 // 3 + 1
    }

    @Test
    fun `brewing when machine is OFF propagates domain error`() {
        // Given
        val machineRepository =
            FakeCoffeeMachineRepository(CoffeeMachineFixture.unpoweredMachine)
        val recipeRepository = FakeRecipeRepository(espresso)
        val publisher = CollectingPublisher()
        val brewCoffee =
            BrewCoffeeUseCase(machineRepository, recipeRepository, publisher)

        // When
        val ex =
            shouldThrow<IllegalArgumentException> {
                brewCoffee(Recipe.Name.ESPRESSO)
            }

        // Then
        ex.message shouldBe "Machine is OFF"

        publisher.published.size shouldBe 0
    }
}

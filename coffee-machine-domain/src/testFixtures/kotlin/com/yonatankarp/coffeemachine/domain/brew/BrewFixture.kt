package com.yonatankarp.coffeemachine.domain.brew

import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import com.yonatankarp.coffeemachine.domain.recipe.RecipeFixture
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import java.time.Instant

object BrewFixture {
    val started
        get() =
            Brew(
                id = Brew.Id.new(),
                machineId = CoffeeMachineFixture.poweredMachine.id,
                version = 0,
                recipe = RecipeFixture.espresso,
                state = Brew.State.STARTED,
                totalSeconds = RecipeFixture.espresso.brewSeconds,
                consumedWater = Milliliters.ZERO,
                consumedBeans = Grams.ZERO,
                startedAt = Instant.now(),
                finishedAt = null,
                cancelledAt = null,
            )

    val brewing
        get() =
            Brew(
                id = Brew.Id.new(),
                machineId = CoffeeMachineFixture.poweredMachine.id,
                version = 0,
                recipe = RecipeFixture.espresso,
                state = Brew.State.BREWING,
                totalSeconds = RecipeFixture.espresso.brewSeconds,
                consumedWater = RecipeFixture.espresso.water,
                consumedBeans = RecipeFixture.espresso.beans,
                startedAt = Instant.now(),
                finishedAt = null,
                cancelledAt = null,
            )

    val finished
        get() =
            Brew(
                id = Brew.Id.new(),
                machineId = CoffeeMachineFixture.poweredMachine.id,
                version = 0,
                recipe = RecipeFixture.espresso,
                state = Brew.State.FINISHED,
                totalSeconds = RecipeFixture.espresso.brewSeconds,
                consumedWater = RecipeFixture.espresso.water,
                consumedBeans = RecipeFixture.espresso.beans,
                startedAt = Instant.now(),
                finishedAt = Instant.now(),
                cancelledAt = null,
            )

    val canceled
        get() =
            Brew(
                id = Brew.Id.new(),
                machineId = CoffeeMachineFixture.poweredMachine.id,
                version = 0,
                recipe = RecipeFixture.espresso,
                state = Brew.State.CANCELLED,
                totalSeconds = RecipeFixture.espresso.brewSeconds,
                consumedWater = Milliliters.ZERO,
                consumedBeans = Grams.ZERO,
                startedAt = Instant.now(),
                finishedAt = null,
                cancelledAt = Instant.now(),
            )
}

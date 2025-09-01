package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.BrewEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.BrewStateEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.RecipeMapper.toEntity
import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.recipe.BrewSeconds
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds

object BrewMapper {
    fun BrewEntity.toDomain() =
        Brew(
            id = Brew.Id(id),
            machineId = CoffeeMachine.Id(machineId),
            version = version,
            recipe = recipe.toDomain(),
            state = state.toDomain(),
            totalSeconds = BrewSeconds(Seconds(totalSeconds)),
            consumedWater = Milliliters(consumedWater),
            consumedBeans = Grams(consumedBeans),
            startedAt = startedAt,
            finishedAt = finishedAt,
            cancelledAt = cancelledAt,
        )

    fun Brew.toEntity() =
        BrewEntity(
            id = id.value,
            version = version,
            machineId = machineId.value,
            recipe = recipe.toEntity(),
            state = state.toEntity(),
            totalSeconds = totalSeconds.second.value,
            consumedWater = consumedWater.value,
            consumedBeans = consumedBeans.value,
            startedAt = startedAt,
            finishedAt = finishedAt,
            cancelledAt = cancelledAt,
        )

    private fun BrewStateEntity.toDomain() =
        when (this) {
            BrewStateEntity.STARTED -> Brew.State.STARTED
            BrewStateEntity.BREWING -> Brew.State.BREWING
            BrewStateEntity.FINISHED -> Brew.State.FINISHED
            BrewStateEntity.CANCELLED -> Brew.State.CANCELLED
        }

    private fun Brew.State.toEntity() =
        when (this) {
            Brew.State.STARTED -> BrewStateEntity.STARTED
            Brew.State.BREWING -> BrewStateEntity.BREWING
            Brew.State.FINISHED -> BrewStateEntity.FINISHED
            Brew.State.CANCELLED -> BrewStateEntity.CANCELLED
        }
}

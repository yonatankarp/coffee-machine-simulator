package com.yonatankarp.coffeemachine.application.ports.input

import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import com.yonatankarp.coffeemachine.domain.recipe.Recipe

fun interface BrewCoffeePort {
    operator fun invoke(recipeName: Recipe.Name): List<DomainEvent>
}

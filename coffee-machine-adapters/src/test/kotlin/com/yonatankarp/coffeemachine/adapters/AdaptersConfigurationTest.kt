package com.yonatankarp.coffeemachine.adapters

import com.yonatankarp.coffeemachine.adapters.output.db.h2.CoffeeMachineH2JpaRepository
import com.yonatankarp.coffeemachine.adapters.output.db.h2.RecipeH2JpaRepository
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AdaptersConfigurationTest(
    private val coffeeMachineH2JpaRepository: CoffeeMachineH2JpaRepository,
    private val recipeH2JpaRepository: RecipeH2JpaRepository,
) {
    @Test
    fun `context loads`() {
        coffeeMachineH2JpaRepository.shouldNotBeNull()
        recipeH2JpaRepository.shouldNotBeNull()
    }
}

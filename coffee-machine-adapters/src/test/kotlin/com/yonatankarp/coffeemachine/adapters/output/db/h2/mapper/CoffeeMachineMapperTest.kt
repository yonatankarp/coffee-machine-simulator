package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.CoffeeMachineEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toEntity
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoffeeMachineMapperTest {
    @Test
    fun `should map from entity to domain`() {
        // Given
        val coffeeMachine = CoffeeMachineFixture.defaultMachine()
        val entity =
            CoffeeMachineEntity(
                id = coffeeMachine.id.value,
                version = coffeeMachine.version,
                model = coffeeMachine.model.value,
                waterCapacity = coffeeMachine.waterTank.capacity.value,
                waterCurrent = coffeeMachine.waterTank.current.value,
                beansCapacity = coffeeMachine.beanHopper.capacity.value,
                beansCurrent = coffeeMachine.beanHopper.current.value,
                wasteCapacityPucks = coffeeMachine.wasteBin.capacityPucks,
                wasteCurrentPucks = coffeeMachine.wasteBin.currentPucks,
                poweredOn = coffeeMachine.poweredOn,
            )

        // When
        val domain = entity.toDomain()

        // Then
        domain.id shouldBe coffeeMachine.id
        domain.version shouldBe coffeeMachine.version
        domain.model shouldBe coffeeMachine.model
        domain.waterTank shouldBe coffeeMachine.waterTank
        domain.beanHopper shouldBe coffeeMachine.beanHopper
        domain.wasteBin shouldBe coffeeMachine.wasteBin
        domain.poweredOn shouldBe coffeeMachine.poweredOn
    }

    @Test
    fun `should map from domain to entity`() {
        // Given
        val domain = CoffeeMachineFixture.defaultMachine()

        // When
        val entity = domain.toEntity()

        // Then
        entity.id shouldBe domain.id.value
        entity.version shouldBe domain.version
        entity.model shouldBe domain.model.value
        entity.waterCapacity shouldBe domain.waterTank.capacity.value
        entity.waterCurrent shouldBe domain.waterTank.current.value
        entity.beansCapacity shouldBe domain.beanHopper.capacity.value
        entity.beansCurrent shouldBe domain.beanHopper.current.value
        entity.wasteCapacityPucks shouldBe domain.wasteBin.capacityPucks
        entity.wasteCurrentPucks shouldBe domain.wasteBin.currentPucks
        entity.poweredOn shouldBe domain.poweredOn
    }
}

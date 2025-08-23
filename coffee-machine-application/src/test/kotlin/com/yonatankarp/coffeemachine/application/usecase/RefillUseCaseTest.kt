package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.fakes.FakeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import com.yonatankarp.coffeemachine.domain.machine.RefillType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RefillUseCaseTest {
    @Test
    fun `refill updates reservoirs and returns status DTO`() {
        // Given
        val machineRepository = FakeMachineRepository(CoffeeMachineFixture.poweredMachine)
        val refill = RefillUseCase(machineRepository)

        // When
        val afterWater = refill(RefillType.WATER)
        val afterBeans = refill(RefillType.BEANS)
        val afterWaste = refill(RefillType.WASTE)

        // Then
        afterWater.water.current shouldBe afterWater.water.capacity
        afterBeans.beans.current shouldBe afterBeans.beans.capacity
        afterWaste.waste.currentPucks shouldBe 0
    }
}

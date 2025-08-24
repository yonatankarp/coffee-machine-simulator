package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.fakes.FakeCoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PowerUseCaseTest {
    @Test
    fun `power toggles machine on and off`() {
        // Given
        val machineRepository = FakeCoffeeMachineRepository(CoffeeMachineFixture.unpoweredMachine)
        val powerMachine = CoffeeMachinePowerUseCase(machineRepository)

        // When
        val onStatus = powerMachine(true)
        val offStatus = powerMachine(false)

        // Then
        onStatus.poweredOn shouldBe true
        offStatus.poweredOn shouldBe false
    }
}

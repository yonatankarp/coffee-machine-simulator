package com.yonatankarp.coffeemachine.application.usecase

import com.yonatankarp.coffeemachine.application.fakes.FakeCoffeeMachineRepository
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture.poweredMachine
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StatusUseCaseTest {
    @Test
    fun `status reflects repository state`() {
        // Given
        val machineRepository = FakeCoffeeMachineRepository(poweredMachine)
        val machineStatus = CoffeeMachineStatusUseCase(machineRepository)

        // When
        val status = machineStatus()

        // Then
        status.model shouldBe poweredMachine.model
        status.poweredOn shouldBe poweredMachine.poweredOn
        status.water.current shouldBe poweredMachine.waterTank.current
        status.water.capacity shouldBe poweredMachine.waterTank.capacity
        status.beans.current shouldBe poweredMachine.beanHopper.current
        status.beans.capacity shouldBe poweredMachine.beanHopper.capacity
        status.waste.currentPucks shouldBe poweredMachine.wasteBin.currentPucks
        status.waste.capacityPucks shouldBe poweredMachine.wasteBin.capacityPucks
    }
}

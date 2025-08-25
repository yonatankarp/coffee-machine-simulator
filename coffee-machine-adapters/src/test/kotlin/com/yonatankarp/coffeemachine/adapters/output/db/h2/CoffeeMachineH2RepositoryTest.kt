package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toEntity
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor

@DataJpaTest(showSql = true)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CoffeeMachineH2RepositoryTest(
    private val jpaRepository: CoffeeMachineH2JpaRepository,
) {
    @Test
    fun `should save coffee machine`() {
        // Given
        val coffeeMachine = CoffeeMachineFixture.defaultMachine()
        val repository = CoffeeMachineH2Repository(jpaRepository)

        // When
        repository.save(coffeeMachine)

        // Then
        val savedCoffeeMachine = jpaRepository.findByIdOrNull(CoffeeMachineId.default().value)?.toDomain()
        savedCoffeeMachine shouldBe coffeeMachine
    }

    @Test
    fun `should load existing coffee machine`() {
        // Given
        val coffeeMachine = CoffeeMachineFixture.defaultMachine()
        jpaRepository.save(coffeeMachine.toEntity())
        val repository = CoffeeMachineH2Repository(jpaRepository)

        // When
        val loadedCoffeeMachine = repository.load()

        // Then
        loadedCoffeeMachine shouldBe coffeeMachine.copy(version = coffeeMachine.version + 1)
    }
}

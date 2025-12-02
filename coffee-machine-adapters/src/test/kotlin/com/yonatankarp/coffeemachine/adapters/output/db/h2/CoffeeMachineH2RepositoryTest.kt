package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toEntity
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachineFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        "spring.main.web-application-type=none",
        "spring.flyway.enabled=true",
        "spring.jpa.hibernate.ddl-auto=none",
    ],
)
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CoffeeMachineH2RepositoryTest(
    private val jpaRepository: CoffeeMachineH2JpaRepository,
) {
    @BeforeEach
    fun setUp() {
        jpaRepository.deleteAll()
    }

    @Test
    fun `should save coffee machine`() {
        // Given
        val coffeeMachine = CoffeeMachineFixture.defaultMachine()
        val repository = CoffeeMachineH2Repository(jpaRepository)

        // When
        repository.save(coffeeMachine)

        // Then
        val savedCoffeeMachine = jpaRepository.findByIdOrNull(CoffeeMachine.Id.default().value)?.toDomain()
        savedCoffeeMachine shouldBe coffeeMachine
    }

    @Test
    fun `should load existing coffee machine`() {
        // Given
        val coffeeMachine = CoffeeMachineFixture.defaultMachine()
        val savedEntity = jpaRepository.save(coffeeMachine.toEntity())
        val repository = CoffeeMachineH2Repository(jpaRepository)

        // When
        val loadedCoffeeMachine = repository.load()

        // Then
        loadedCoffeeMachine shouldBe savedEntity.toDomain()
    }
}

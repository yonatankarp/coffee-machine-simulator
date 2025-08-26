package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.CoffeeMachineMapper.toEntity
import com.yonatankarp.coffeemachine.domain.machine.CoffeeMachine
import com.yonatankarp.coffeemachine.domain.machine.exception.CoffeeMachineException
import com.yonatankarp.coffeemachine.domain.machine.port.CoffeeMachineRepository
import org.springframework.stereotype.Repository

@Repository
class CoffeeMachineH2Repository(
    private val jpaRepository: CoffeeMachineH2JpaRepository,
) : CoffeeMachineRepository {
    override fun load(): CoffeeMachine =
        jpaRepository.findAll().firstOrNull()?.toDomain()
            ?: throw CoffeeMachineException.NoCoffeeMachineFound()

    override fun save(machine: CoffeeMachine): CoffeeMachine = jpaRepository.save(machine.toEntity()).toDomain()
}

package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.BrewMapper.toDomain
import com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper.BrewMapper.toEntity
import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.brew.port.BrewRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class BrewH2Repository(
    private val jpaRepository: BrewH2JpaRepository,
) : BrewRepository {
    override fun save(brew: Brew): Brew = jpaRepository.save(brew.toEntity()).toDomain()

    override fun findById(id: Brew.Id): Brew? = jpaRepository.findByIdOrNull(id.value)?.toDomain()
}

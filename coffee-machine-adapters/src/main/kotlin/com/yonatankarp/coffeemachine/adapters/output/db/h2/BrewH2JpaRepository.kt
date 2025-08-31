package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.BrewEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BrewH2JpaRepository : CrudRepository<BrewEntity, UUID>

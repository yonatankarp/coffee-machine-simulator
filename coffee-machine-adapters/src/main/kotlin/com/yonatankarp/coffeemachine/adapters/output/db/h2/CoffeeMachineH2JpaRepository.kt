package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.CoffeeMachineEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface CoffeeMachineH2JpaRepository : CrudRepository<CoffeeMachineEntity, UUID>

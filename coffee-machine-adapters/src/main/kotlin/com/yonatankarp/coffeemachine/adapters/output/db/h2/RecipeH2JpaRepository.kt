package com.yonatankarp.coffeemachine.adapters.output.db.h2

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeEntity
import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.RecipeNameEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RecipeH2JpaRepository : CrudRepository<RecipeEntity, UUID> {
    fun findByName(name: RecipeNameEntity): RecipeEntity?
}

package com.yonatankarp.coffeemachine.adapters.output.db.h2.mapper

import com.yonatankarp.coffeemachine.adapters.output.db.h2.entity.GrindSizeEntity
import com.yonatankarp.coffeemachine.domain.recipe.Recipe

object GrindSizeMapper {
    fun GrindSizeEntity.toDomain() =
        when (this) {
            GrindSizeEntity.FINE -> Recipe.GrindSize.FINE
            GrindSizeEntity.MEDIUM -> Recipe.GrindSize.MEDIUM
            GrindSizeEntity.COARSE -> Recipe.GrindSize.COARSE
        }

    fun Recipe.GrindSize.toEntity() =
        when (this) {
            Recipe.GrindSize.FINE -> GrindSizeEntity.FINE
            Recipe.GrindSize.MEDIUM -> GrindSizeEntity.MEDIUM
            Recipe.GrindSize.COARSE -> GrindSizeEntity.COARSE
        }
}

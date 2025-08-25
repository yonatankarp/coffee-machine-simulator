package com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper

import com.yonatankarp.coffeemachine.domain.machine.RefillType
import com.yonatankarp.coffeemachine.openapi.model.RefillRequest

object RefillTypeMapper {
    fun RefillRequest.Type.toDomain() =
        when (this) {
            RefillRequest.Type.WATER -> RefillType.WATER
            RefillRequest.Type.BEANS -> RefillType.BEANS
            RefillRequest.Type.WASTE -> RefillType.WASTE
        }
}

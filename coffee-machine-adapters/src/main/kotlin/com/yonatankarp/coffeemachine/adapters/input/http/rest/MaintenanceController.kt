package com.yonatankarp.coffeemachine.adapters.input.http.rest

import com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper.RefillTypeMapper.toDomain
import com.yonatankarp.coffeemachine.application.ports.input.RefillMachine
import com.yonatankarp.coffeemachine.openapi.model.RefillRequest
import com.yonatankarp.coffeemachine.openapi.model.RefillResponse
import com.yonatankarp.coffeemachine.openapi.v1.apis.MaintenanceApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MaintenanceController(
    private val refillMachine: RefillMachine,
) : MaintenanceApi {
    override fun refill(refillRequest: RefillRequest): ResponseEntity<RefillResponse> {
        refillMachine(refillRequest.type.toDomain())
        return ResponseEntity.ok(RefillResponse(true, "${refillRequest.type} filled to capacity"))
    }
}

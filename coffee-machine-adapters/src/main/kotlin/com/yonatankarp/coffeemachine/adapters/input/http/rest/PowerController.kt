package com.yonatankarp.coffeemachine.adapters.input.http.rest

import com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper.MachineStatusMapper.toApi
import com.yonatankarp.coffeemachine.application.ports.input.ManageMachine
import com.yonatankarp.coffeemachine.openapi.model.MachineStatus
import com.yonatankarp.coffeemachine.openapi.v1.apis.PowerApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PowerController(
    private val manageMachine: ManageMachine,
) : PowerApi {
    override fun switchPower(powerStatus: String): ResponseEntity<MachineStatus> {
        val isPowerOn = powerStatus.lowercase() == "on"
        return ResponseEntity.ok(manageMachine(isPowerOn).toApi())
    }
}

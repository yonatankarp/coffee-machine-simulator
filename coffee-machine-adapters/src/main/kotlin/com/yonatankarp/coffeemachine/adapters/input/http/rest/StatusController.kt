package com.yonatankarp.coffeemachine.adapters.input.http.rest

import com.yonatankarp.coffeemachine.adapters.input.http.brew.BrewJobService
import com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper.MachineStatusMapper.toApi
import com.yonatankarp.coffeemachine.application.ports.input.GetMachineStatus
import com.yonatankarp.coffeemachine.openapi.model.MachineStatus
import com.yonatankarp.coffeemachine.openapi.v1.apis.StatusApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusController(
    private val getMachineStatus: GetMachineStatus,
    private val brewJobService: BrewJobService,
) : StatusApi {
    override fun getStatus(): ResponseEntity<MachineStatus> {
        val base = getMachineStatus()
        val overlay = brewJobService.overlayStatus(base)
        return ResponseEntity.ok(overlay.toApi())
    }
}

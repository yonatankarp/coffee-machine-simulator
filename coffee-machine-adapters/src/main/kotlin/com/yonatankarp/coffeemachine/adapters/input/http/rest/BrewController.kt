package com.yonatankarp.coffeemachine.adapters.input.http.rest

import com.yonatankarp.coffeemachine.adapters.input.http.brew.BrewJobService
import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffee
import com.yonatankarp.coffeemachine.application.ports.input.BrowseRecipes
import com.yonatankarp.coffeemachine.openapi.model.StartBrewRequest
import com.yonatankarp.coffeemachine.openapi.model.StartBrewResponse
import com.yonatankarp.coffeemachine.openapi.v1.apis.BrewApi
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class BrewController(
    private val brewCoffee: BrewCoffee,
    private val browseRecipes: BrowseRecipes,
    private val brewJobService: BrewJobService,
) : BrewApi {
    private val log = KotlinLogging.logger {}

    override fun startBrew(startBrewRequest: StartBrewRequest): ResponseEntity<StartBrewResponse> {
        val recipe =
            browseRecipes().firstOrNull { it.id.value == startBrewRequest.recipeId }
                ?: return ResponseEntity.ok(StartBrewResponse(started = false, message = "Recipe not found"))

        val started =
            brewJobService.startBrew(recipe) {
                brewCoffee(recipe.name)
            }

        return if (started) {
            log.info { "Brew accepted for recipe ${recipe.name}" }
            ResponseEntity.ok(StartBrewResponse(true, "Brewing ${recipe.name.displayName} for ${recipe.brewSeconds}"))
        } else {
            ResponseEntity.ok(StartBrewResponse(false, "Machine is already brewing"))
        }
    }

    override fun cancelBrew(): ResponseEntity<StartBrewResponse> {
        val cancelled = brewJobService.cancel()
        return if (cancelled) {
            log.info { "Brew cancelled" }
            ResponseEntity.ok(StartBrewResponse(true, "Brew cancelled"))
        } else {
            ResponseEntity.ok(StartBrewResponse(false, "No brew in progress"))
        }
    }
}

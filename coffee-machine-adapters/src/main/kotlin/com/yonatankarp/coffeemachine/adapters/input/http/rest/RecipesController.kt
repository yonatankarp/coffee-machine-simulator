package com.yonatankarp.coffeemachine.adapters.input.http.rest

import com.yonatankarp.coffeemachine.adapters.input.http.rest.mapper.RecipeMapper.toApi
import com.yonatankarp.coffeemachine.application.ports.input.BrowseRecipes
import com.yonatankarp.coffeemachine.openapi.model.Recipe
import com.yonatankarp.coffeemachine.openapi.v1.apis.RecipesApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipesController(
    private val browseRecipes: BrowseRecipes,
) : RecipesApi {
    override fun listRecipes(): ResponseEntity<List<Recipe>> = ResponseEntity.ok(browseRecipes().toApi())
}

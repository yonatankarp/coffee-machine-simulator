package com.yonatankarp.coffeemachine.domain.brew.port

import com.yonatankarp.coffeemachine.domain.brew.Brew

interface BrewRepository {
    fun save(brew: Brew): Brew

    fun findById(id: Brew.Id): Brew?
}

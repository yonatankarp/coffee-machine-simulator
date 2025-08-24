package com.yonatankarp.coffeemachine.adapters.output.db.h2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.util.UUID

@Entity
@Immutable
@Table(name = "recipe")
class RecipeEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,
    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    val name: RecipeNameEntity,
    @Column(name = "water", nullable = false)
    val water: Double,
    @Column(name = "beans", nullable = false)
    val beans: Double,
    @Column(name = "temperature", nullable = false)
    val temperature: Double,
    @Column(name = "grind", nullable = false)
    @Enumerated(EnumType.STRING)
    val grind: GrindSizeEntity,
    @Column(name = "brew_seconds", nullable = false)
    val brewSeconds: Double,
)

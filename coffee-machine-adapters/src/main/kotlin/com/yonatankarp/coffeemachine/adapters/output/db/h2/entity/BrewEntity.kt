package com.yonatankarp.coffeemachine.adapters.output.db.h2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "brews")
data class BrewEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,
    @Version
    @Column(name = "version", nullable = false)
    val version: Long,
    @Column(name = "machine_id", nullable = false)
    val machineId: UUID,
    @OneToOne
    @JoinColumn(name = "recipe_id")
    val recipe: RecipeEntity,
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    val state: BrewStateEntity,
    @Column(name = "total_seconds", nullable = false)
    val totalSeconds: Double,
    @Column(name = "consumed_water", nullable = false)
    val consumedWater: Double,
    @Column(name = "consumed_beans", nullable = false)
    val consumedBeans: Double,
    @Column(name = "started_at", nullable = false)
    val startedAt: Instant,
    @Column(name = "finished_at")
    val finishedAt: Instant?,
    @Column(name = "cancelled_at")
    val cancelledAt: Instant?,
)

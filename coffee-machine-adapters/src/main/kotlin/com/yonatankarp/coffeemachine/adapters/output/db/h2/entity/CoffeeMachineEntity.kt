package com.yonatankarp.coffeemachine.adapters.output.db.h2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID

@Entity
@Table(name = "coffee_machines")
data class CoffeeMachineEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,
    @Version
    @Column(name = "version", nullable = false)
    val version: Long,
    @Column(name = "model", nullable = false)
    val model: String,
    @Column(name = "water_capacity", nullable = false)
    val waterCapacity: Double,
    @Column(name = "water_current", nullable = false)
    val waterCurrent: Double,
    @Column(name = "beans_capacity", nullable = false)
    val beansCapacity: Double,
    @Column(name = "beans_current", nullable = false)
    val beansCurrent: Double,
    @Column(name = "waste_capacity_pucks", nullable = false)
    val wasteCapacityPucks: Int,
    @Column(name = "waste_current_pucks", nullable = false)
    val wasteCurrentPucks: Int,
    @Column(name = "powered_on", nullable = false)
    val poweredOn: Boolean,
)

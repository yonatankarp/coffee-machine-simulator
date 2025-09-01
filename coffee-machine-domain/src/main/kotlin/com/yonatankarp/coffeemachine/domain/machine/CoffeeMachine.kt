package com.yonatankarp.coffeemachine.domain.machine

import com.yonatankarp.coffeemachine.domain.brew.Brew
import com.yonatankarp.coffeemachine.domain.brew.Brewer
import com.yonatankarp.coffeemachine.domain.machine.event.DomainEvent
import java.time.Instant
import java.util.UUID

data class CoffeeMachine(
    val id: Id,
    val version: Long,
    val model: Model,
    val waterTank: WaterTank,
    val beanHopper: BeanHopper,
    val wasteBin: WasteBin,
    val poweredOn: Boolean = false,
) {
    fun powerOn() = copy(poweredOn = true)

    fun powerOff() = copy(poweredOn = false)

    /**
     * Convenience wrapper over [copy] to replace one or more components while preserving the rest.
     * Note: This is a member function and not the Kotlin stdlib `with(receiver) { }`.
     */
    fun with(
        waterTank: WaterTank = this.waterTank,
        beanHopper: BeanHopper = this.beanHopper,
        wasteBin: WasteBin = this.wasteBin,
    ) = copy(
        waterTank = waterTank,
        beanHopper = beanHopper,
        wasteBin = wasteBin,
    )

    /**
     * Progressive brewing/ticking.
     *
     * Ensures beans are consumed up-front when switching STARTED -> BREWING, and water is consumed
     * gradually according to elapsed time. When time reaches total, the brew is finished and a puck
     * is added to the waste bin. Returns the updated machine, brew, and emitted domain events.
     */
    fun brew(
        brew: Brew,
        now: Instant,
        autoComplete: Boolean = true,
    ): Outcome {
        val result =
            Brewer(
                initialMachine = this,
                initialBrew = brew,
                now = now,
                autoComplete = autoComplete,
            ).tick()
        return Outcome(
            updatedMachine = result.machine,
            updatedBrew = result.brew,
            events = result.events,
        )
    }

    fun refill(type: RefillType): CoffeeMachine =
        when (type) {
            RefillType.WATER -> with(waterTank = waterTank.refill())
            RefillType.BEANS -> with(beanHopper = beanHopper.refill())
            RefillType.WASTE -> with(wasteBin = wasteBin.empty())
        }

    @JvmInline
    value class Model(
        val value: String,
    ) {
        override fun toString(): String = value
    }

    data class Outcome(
        val updatedMachine: CoffeeMachine,
        val updatedBrew: Brew,
        val events: List<DomainEvent>,
    )

    @JvmInline
    value class Id(
        val value: UUID,
    ) {
        companion object {
            fun default() = Id(UUID.fromString("00000000-0000-0000-0000-000000000000"))

            fun new() = Id(UUID.randomUUID())

            fun from(value: String) = Id(UUID.fromString(value))
        }
    }
}

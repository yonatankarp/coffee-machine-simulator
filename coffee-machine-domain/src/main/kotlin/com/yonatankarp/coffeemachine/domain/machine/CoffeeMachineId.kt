package com.yonatankarp.coffeemachine.domain.machine

import java.util.UUID

@JvmInline
value class CoffeeMachineId(
    val value: UUID,
) {
    companion object {
        fun default() = CoffeeMachineId(UUID.fromString("00000000-0000-0000-0000-000000000000"))

        fun new() = CoffeeMachineId(UUID.randomUUID())

        fun from(value: String) = CoffeeMachineId(UUID.fromString(value))
    }
}

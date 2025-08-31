package com.yonatankarp.coffeemachine.domain.brew

import com.yonatankarp.coffeemachine.domain.shared.unit.Seconds
import java.time.Instant
import kotlin.math.min

data class BrewProgress(
    val elapsed: Seconds,
    val total: Seconds,
    val ratio: Double,
) {
    companion object {
        fun of(
            startedAt: Instant,
            total: Seconds,
            now: Instant,
        ): BrewProgress {
            val elapsedSec =
                Seconds.from(
                    maxOf(
                        0L,
                        now.toEpochMilli() - startedAt.toEpochMilli(),
                    ),
                )
            val clamped = Seconds(min(elapsedSec.value, total.value))
            val ratio =
                if (total <= Seconds.ZERO) 1.0 else (clamped.value / total.value)
            return BrewProgress(
                elapsed = clamped,
                total = total,
                ratio = ratio.coerceIn(0.0, 1.0),
            )
        }
    }
}

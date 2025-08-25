package com.yonatankarp.coffeemachine.adapters.input.http.brew

import com.yonatankarp.coffeemachine.application.ports.input.BrewCoffee
import com.yonatankarp.coffeemachine.domain.machine.status.BeansStatus
import com.yonatankarp.coffeemachine.domain.machine.status.MachineStatus
import com.yonatankarp.coffeemachine.domain.machine.status.WasteStatus
import com.yonatankarp.coffeemachine.domain.machine.status.WaterStatus
import com.yonatankarp.coffeemachine.domain.recipe.Recipe
import com.yonatankarp.coffeemachine.domain.shared.unit.Grams
import com.yonatankarp.coffeemachine.domain.shared.unit.Milliliters
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@Service
class BrewJobService(
    private val scheduler: ScheduledExecutorService,
    private val brewCoffee: BrewCoffee,
) {
    private val logger = KotlinLogging.logger {}

    data class BrewJob(
        val recipe: Recipe,
        val startedAt: Instant,
        val totalSeconds: Int,
        @Volatile var elapsedSeconds: Int = 0,
        @Volatile var completed: Boolean = false,
        val commit: () -> Unit,
    )

    private val currentJob = AtomicReference<BrewJob?>(null)

    fun startBrew(
        recipe: Recipe,
        commit: () -> Unit,
    ): Boolean {
        if (currentJob.get() != null) return false
        val job =
            BrewJob(
                recipe = recipe,
                startedAt = Instant.now(),
                totalSeconds =
                    recipe.brewSeconds.second.value
                        .toInt(),
                commit = commit,
            )
        currentJob.set(job)
        logger.info { "Brew started for recipe ${recipe.name} (${recipe.brewSeconds})" }

        scheduler.scheduleAtFixedRate({
            val job = currentJob.get() ?: return@scheduleAtFixedRate
            if (job.completed) return@scheduleAtFixedRate
            job.elapsedSeconds++
            if (job.elapsedSeconds >= job.totalSeconds) {
                try {
                    job.completed = true
                    job.elapsedSeconds = job.totalSeconds
                    job.commit.invoke() // commit domain mutation once at the end
                    logger.info { "Brew completed for recipe ${job.recipe.name}" }
                } catch (e: Exception) {
                    logger.error(e) { "Commit brew failed: ${e.message}" }
                } finally {
                    currentJob.set(null)
                }
            }
        }, 1, 1, TimeUnit.SECONDS)

        brewCoffee(recipe.name)

        return true
    }

    /**
     * Combines the base status with proportional deltas for a running brew.
     * Only visual: domain is mutated at the end via commit().
     */
    fun overlayStatus(base: MachineStatus): MachineStatus {
        val job = currentJob.get() ?: return base
        val ratio: Double =
            if (job.totalSeconds == 0) {
                1.0
            } else {
                job.elapsedSeconds.toDouble() / job.totalSeconds.toDouble()
            }

        // Proportionally reduce water/beans during brew
        val waterUse = WaterStatus(Milliliters(job.recipe.water.value * ratio), base.water.capacity)
        val beansUse = BeansStatus(Grams(job.recipe.beans.value * ratio), base.beans.capacity)
        val wasteUse = WasteStatus(base.waste.currentPucks + 1, base.waste.capacityPucks)

        val newWater = (base.water - waterUse)
        val newBeans = (base.beans - beansUse)
        val newWaste = (base.waste + wasteUse)

//        return BrewJob(
//            recipe = base.copy(
//                water = newWater,
//                beans = newBeans,
//                waste = newWaste,
//            ),
//            val startedAt: Instant,
//        val totalSeconds: Int,
//        @Volatile var elapsedSeconds: Int = 0,
//        @Volatile var completed: Boolean = false,
//        val commit: () -> Unit,
//        )

        return base.copy(
            water = newWater,
            beans = newBeans,
            waste = newWaste,
        )
    }

    fun cancel(): Boolean {
        val job = currentJob.get() ?: return false
        job.completed = true
        currentJob.set(null)
        return true
    }
}

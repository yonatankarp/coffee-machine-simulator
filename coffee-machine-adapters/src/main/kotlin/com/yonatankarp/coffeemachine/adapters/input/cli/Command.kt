package com.yonatankarp.coffeemachine.adapters.input.cli

sealed interface Command {
    data object Help : Command

    data object Quit : Command

    data class Power(
        val on: Boolean,
    ) : Command

    data object Status : Command

    data object Recipes : Command

    data class Brew(
        val recipeName: String,
    ) : Command

    data class RefillWater(
        val ml: Double?,
    ) : Command

    data class RefillBeans(
        val g: Double?,
    ) : Command

    data object EmptyWaste : Command

    companion object {
        val help =
            """
            Commands:
              help                          - show this help
              quit                          - exit
              power on|off                  - turn machine on/off
              status                        - show machine status
              recipes                       - list available recipes
              brew <RECIPE_NAME>            - brew a recipe (e.g., ESPRESSO)
              refill water [ml]             - refill water tank (full if ml omitted)
              refill beans [g]              - refill bean hopper (full if g omitted)
              refill waste                  - empty waste bin
            """.trimIndent()

        fun parse(line: String): Command? {
            val parts = line.splitToCommandParts()

            if (parts.isEmpty()) return null

            return when (parts.first().lowercase()) {
                "help", "h", "?" -> Help
                "quit", "exit", "q" -> Quit
                "power" -> parts.parsePower()
                "status" -> Status
                "recipes" -> Recipes
                "brew" -> parts.parseBrew()
                "refill" -> parts.praseRefill()
                else -> null
            }
        }

        private fun String.splitToCommandParts() =
            trim()
                .split("""\s+""".toRegex())
                .filter { it.isNotEmpty() }

        private fun List<String>.parsePower(): Power? =
            when (getOrNull(1)?.lowercase()) {
                "on" -> Power(true)
                "off" -> Power(false)
                else -> null
            }

        private fun List<String>.parseBrew(): Brew? = getOrNull(1)?.let { Brew(it.uppercase()) }

        private fun List<String>.praseRefill(): Command? =
            when (getOrNull(1)?.lowercase()) {
                "water" -> RefillWater(getOrNull(2)?.toDoubleOrNull())
                "beans" -> RefillBeans(getOrNull(2)?.toDoubleOrNull())
                "waste" -> EmptyWaste
                else -> null
            }
    }
}

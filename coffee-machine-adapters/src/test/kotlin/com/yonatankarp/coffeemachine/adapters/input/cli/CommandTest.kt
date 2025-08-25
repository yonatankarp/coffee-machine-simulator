package com.yonatankarp.coffeemachine.adapters.input.cli

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CommandTest {
    @Test
    fun `should parse 'help' command`() {
        Command.parse("help") shouldBe Command.Help
        Command.parse("h") shouldBe Command.Help
        Command.parse("?") shouldBe Command.Help
    }

    @Test
    fun `should parse 'quit' command`() {
        Command.parse("quit") shouldBe Command.Quit
        Command.parse("exit") shouldBe Command.Quit
        Command.parse("q") shouldBe Command.Quit
    }

    @Test
    fun `should parse 'power on' command`() {
        Command.parse("power on") shouldBe Command.Power(true)
    }

    @Test
    fun `should parse 'power off' command`() {
        Command.parse("power off") shouldBe Command.Power(false)
    }

    @Test
    fun `should parse 'status' command`() {
        Command.parse("status") shouldBe Command.Status
    }

    @Test
    fun `should parse 'recipes' command`() {
        Command.parse("recipes") shouldBe Command.Recipes
    }

    @Test
    fun `should parse 'brew' command`() {
        Command.parse("brew ESPRESSO") shouldBe Command.Brew("ESPRESSO")
    }

    @Test
    fun `should parse 'refill water' command`() {
        Command.parse("refill water") shouldBe Command.RefillWater(null)
    }

    @Test
    fun `should parse 'refill water' command with amount`() {
        Command.parse("refill water 500") shouldBe Command.RefillWater(500.0)
    }

    @Test
    fun `should parse 'refill beans' command`() {
        Command.parse("refill beans") shouldBe Command.RefillBeans(null)
    }

    @Test
    fun `should parse 'refill beans' command with amount`() {
        Command.parse("refill beans 250") shouldBe Command.RefillBeans(250.0)
    }

    @Test
    fun `should parse 'empty waste' command`() {
        Command.parse("refill waste") shouldBe Command.EmptyWaste
    }

    @Test
    fun `should return null for invalid command`() {
        Command.parse("invalid command") shouldBe null
    }
}

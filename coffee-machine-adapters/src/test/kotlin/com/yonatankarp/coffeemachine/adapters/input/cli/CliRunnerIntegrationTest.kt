package com.yonatankarp.coffeemachine.adapters.input.cli

import com.yonatankarp.coffeemachine.support.LogCapture
import com.yonatankarp.coffeemachine.support.WithSystemIn
import com.yonatankarp.coffeemachine.support.readFileAsLines
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("cli", "test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        "spring.main.web-application-type=none",
        "spring.main.banner-mode=off",
        "logging.level.root=INFO",
    ],
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CliRunnerIntegrationTest(
    private val runner: CliRunner,
) {
    @Test
    fun `full CLI happy path emits expected logs`() {
        // Given
        WithSystemIn(*readFileAsLines("cli/cli-execution-script.txt")).use {
            LogCapture.root().use { logs ->
                // When
                runner.run()

                // Then
                logs.messages.shouldContainAll(*readFileAsLines("cli/cli-execution-script-output.txt"))
            }
        }
    }

    @Test
    fun `unknown recipe is handled gracefully`() {
        // Given
        WithSystemIn(
            "brew unknown-recipe",
            "quit",
        ).use {
            LogCapture.root().use { logs ->
                // When
                runner.run()

                // Then
                logs.messages shouldContain "Unknown recipe 'UNKNOWN-RECIPE'. Try 'recipes'."
            }
        }
    }
}

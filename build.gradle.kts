import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.spotless) apply true
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target(
                fileTree(projectDir) {
                    include("**/*.kt")
                    exclude(
                        "**/.gradle/**",
                        "**/build/generated/**"
                    )
                }
            )
            // see https://github.com/shyiko/ktlint#standard-rules
            ktlint("1.5.0")
        }
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        dependsOn("spotlessCheck")
    }
}

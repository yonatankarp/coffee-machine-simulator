import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.springboot)
    alias(libs.plugins.spring.dependency.management)
}

repositories {
    mavenCentral()
}

dependencies {
    // Project
    implementation(project(":coffee-machine-domain"))
    implementation(project(":coffee-machine-application"))

    implementation(libs.bundles.kotlin.all)
    implementation(libs.bundles.springboot.all)
    implementation(libs.bundles.persistence.all)
    implementation(libs.kotlin.logging)

    testImplementation(libs.bundles.unittest.all)
    testImplementation(libs.bundles.test.integration.all)
    testImplementation(testFixtures(project(":coffee-machine-domain")))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvmTarget.get()))
    }
}

tasks.withType<ProcessResources>().configureEach {
    dependsOn("spotlessCheck")
}

tasks.named<BootRun>("bootRun") {
    standardInput = System.`in`
}

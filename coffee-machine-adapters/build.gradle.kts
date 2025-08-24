import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.spring") version "2.2.10"
    id("org.springframework.boot") version "3.5.5"
    id("org.jetbrains.kotlin.plugin.jpa") version "2.2.10"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":coffee-machine-domain"))
    implementation(project(":coffee-machine-application"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Flyway support
    implementation("org.flywaydb:flyway-core:11.11.1")
    implementation("com.h2database:h2:2.3.232")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.2")
    implementation(kotlin("reflect"))


    // H2 in-memory database
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

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

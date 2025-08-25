import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.springboot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.openapi.generator)
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

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/openapi-spec.yaml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    packageName.set("com.yonatankarp.coffeemachine.openapi.v1")
    modelPackage.set("com.yonatankarp.coffeemachine.openapi.model")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "enumPropertyNaming" to "UPPERCASE",
            "interfaceOnly" to "true",
            "implicitHeaders" to "true",
            "hideGenerationTimestamp" to "true",
            "useTags" to "true",
            "documentationProvider" to "none",
            "useSpringBoot3" to "true",
        )
    )
}

sourceSets[SourceSet.MAIN_SOURCE_SET_NAME].java {
    srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/kotlin")
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
    dependsOn("spotlessApply")
}

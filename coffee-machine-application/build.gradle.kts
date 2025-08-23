import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":coffee-machine-domain"))
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.unittest.all)
    testImplementation(testFixtures(project(":coffee-machine-domain")))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvmTarget.get()))
    }
}

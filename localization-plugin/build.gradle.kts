object Versions {
    const val kotlinVersion = "1.3.50"
    const val junitVersion = "5.5.1"
}

plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.10.1"
    kotlin("jvm") version "1.3.50"
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

group = "co.thebeat.localization"
version = "0.5.0"

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitVersion}")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${Versions.junitVersion}")
}

gradlePlugin {
    plugins {
        create("downloadLocalizationsPlugin") {
            id = "co.thebeat.localization"
            implementationClass = "co.thebeat.localization.DownloadLocalizationsPlugin"
        }
    }
}

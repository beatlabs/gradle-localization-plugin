val kotlinVersion: String by extra { "1.3.72" }
val junitVersion: String by extra { "5.5.1" }

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.11.0"
    kotlin("jvm") version "1.3.72"
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

group = "co.thebeat.localization"
version = "0.4.1"

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

gradlePlugin {
    plugins {
        create("downloadLocalizationsPlugin") {
            id = "co.thebeat.localization"
            displayName = "Download localization files plugin"
            description = "Interact with localization providers (such as Transifex) and automate downloading " +
                    "localized files (such as string.xml for android applications)."
            implementationClass = "co.thebeat.localization.DownloadLocalizationsPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/beatlabs/gradle-localization-plugin"
    vcsUrl = "https://github.com/beatlabs/gradle-localization-plugin"
    tags = listOf("localization", "transifex", "android")
}

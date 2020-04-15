plugins {
    id("co.thebeat.localization") version "0.4.1"
}

val transifexAPIToken: String? by project
val transifexResourceSlug: String? by project
val transifexProjectSlug: String? by project

configure<co.thebeat.localization.extensions.TransifexLocalizationExtension> {
    apiToken.set(transifexAPIToken ?: "")
    resourceSlug.set(transifexResourceSlug ?: "")
    projectSlug.set(transifexProjectSlug ?: "")
    localesMap.set(
            mapOf(
                    "main/resources/localization_en" to "en",
                    "main/resources/localization_gr" to "el",
                    "main/resources/localization_de" to "de"
            )
    )
    srcDir.set("$projectDir/src")
}

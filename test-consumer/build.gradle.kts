plugins {
    id("co.thebeat.localization") version "0.3.6"
}

val apiKey: String by project
val resource: String by project
val projectSlugConfig: String by project

transifexLocalization {
    auth = apiKey
    resourceSlug = resource
    projectSlug = projectSlugConfig

    localesMap = HashMap<String, String>().also {
        it["main/res/localization_en"] = "en"
        it["main/res/localization_gr"] = "el_GR"
    }
    srcDir = "${projectDir}/src"
}
package com.beat.localization

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Chris Margonis on 17/07/2018.
 */
open class DownloadLocalizationsPlugin : Plugin<Project> {

    override fun apply(project: Project?) {

        project?.let {
            val extension = it.extensions.create("transifexLocalization", TransifexLocalizationExtension::class.java)
            with(it) {
                task("fetchLocalization")
                    .doLast {
                        System.out.println("Locales available:")
                        val filesMap = mutableMapOf<String, String>()
                        val localesMap = extension.localesMap
                        extension.localesMap.forEach { folder, locale ->
                            System.out.println("Locale map: $folder - $locale")
                            val requestUrl = "https://www.transifex.com/api/2/" +
                                    "project/AndroidApp/resource/${extension.resource}/translation/$locale?mode=default&file=xml"
                            println("Downloading: $locale")

                            val process = ProcessBuilder(
                                "curl", "-L", "--user",
                                extension.auth, "-X", "GET", requestUrl
                            ).start()
                            process.inputStream.reader(Charsets.UTF_8).use {
                                val text = it.readText()
                                text.trim()
                                // We need to use the 4-space indentation because the file is usually changed by humans
                                // That makes the git diff inspection easier
                                val content = text.replace(
                                    "\\n(\\s+)((<string.+</string>)|<!--.+-->)",
                                    "\n    \$2"
                                )
                                filesMap[folder] = content
                            }
                            process.waitFor(10, TimeUnit.SECONDS)
                        }
                        val parentFolder = extension.srcDir
                        if (filesMap.size == localesMap.size) {

                            localesMap.forEach { folder, _ ->
                                val stringsFile = File("$parentFolder/$folder/strings.xml")
                                stringsFile.writeText(filesMap[folder].toString())
                            }
                        } else {
                            throw GradleException("Error downloading strings")
                        }
                        println("All translations downloaded")
                    }
            }
        }
    }
}
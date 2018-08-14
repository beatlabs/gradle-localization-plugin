package co.thebeat.localization.extensions

import co.thebeat.localization.LocalizationExtension
import org.gradle.api.GradleException
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Chris Margonis on 18/07/2018.
 */
open class TransifexLocalizationExtension : LocalizationExtension {
    lateinit var auth: String
    lateinit var resourceSlug: String
    lateinit var localesMap: Map<String, String>
    lateinit var srcDir: String
    lateinit var projectSlug: String

    override fun execute() {
        System.out.println("Locales available:")
        val filesMap = mutableMapOf<String, String>()
        val localesMap = localesMap
        localesMap.forEach { folder, locale ->
            System.out.println("Locale map: $folder - $locale")
            val requestUrl = "https://www.transifex.com/api/2/" +
                    "project/$projectSlug/resource/$resourceSlug/translation/$locale?mode=default&file=xml"
            println("Downloading: $locale")

            val process = ProcessBuilder(
                "curl", "-L", "--user",
                auth, "-X", "GET", requestUrl
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
        val parentFolder = srcDir
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
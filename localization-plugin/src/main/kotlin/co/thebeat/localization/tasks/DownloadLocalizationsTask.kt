package co.thebeat.localization.tasks

import co.thebeat.localization.extensions.TransifexLocalizationExtension
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import org.gradle.workers.WorkerExecutor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("UnstableApiUsage")
internal abstract class DownloadLocalizationsTask @Inject constructor(
        private val extension: TransifexLocalizationExtension
) : DefaultTask() {

    companion object {
        const val NAME = "fetchLocalization"
    }

    @TaskAction
    fun downloadLocalizations() {
        require(!extension.apiToken.get().isBlank()) {
            """
                An Auth token needs to be configured in order to use this plugin.
                ```
                transifexLocalization {
                    authToken = "my-auth-token"
                }
                ```
            """.trimIndent()
        }

        project.serviceOf<WorkerExecutor>()
                .noIsolation()
                .submit(DownloadTranslationsWorkAction::class.java) {
                    apiToken.set(extension.apiToken.get())
                    parentFolder.set(extension.srcDir.get())
                    projectSlug.set(extension.projectSlug.get())
                    resourceSlug.set(extension.resourceSlug.get())
                    localeMap.set(extension.localesMap.get())
                }
    }

    @Suppress("UnstableApiUsage")
    interface DownloadTranslationWorkParameters : WorkParameters {
        val apiToken: Property<String>
        val parentFolder: Property<String>
        val projectSlug: Property<String>
        val resourceSlug: Property<String>
        val localeMap: MapProperty<String, String>
    }

    @Suppress("UnstableApiUsage")
    abstract class DownloadTranslationsWorkAction : WorkAction<DownloadTranslationWorkParameters> {

        private val logger: Logger = Logging.getLogger(
                "TheBEAT:GradleLocalizationPlugin:${DownloadLocalizationsTask::class.java.simpleName}"
        )

        override fun execute() {
            val apiToken = "api:${parameters.apiToken.get()}"
            val localesMap = parameters.localeMap.get()
            val filesMap = mutableMapOf<String, String>()

            localesMap.forEach { (folder, locale) ->
                logger.debug("Will download pair of: $folder - $locale")

                val localeRequestUrl = buildTranslationRequestUrl(currentLocale = locale)

                logger.debug("Built request url: $localeRequestUrl")

                val process = ProcessBuilder(
                        "curl",
                        "-L",
                        "--user",
                        apiToken,
                        "-X",
                        "GET",
                        localeRequestUrl
                ).start()

                logger.debug("Process has started")

                process.inputStream.reader(Charsets.UTF_8).use {
                    val text = it.readText().trim()
                    // We need to use the 4-space indentation because the file is usually changed by humans
                    // That makes the git diff inspection easier
                    val content = text.replace(
                            """\n(\s+)((<string.+</string>)|<!--.+-->)""".toRegex(),
                            "\n    \$2"
                    )
                    filesMap[folder] = content
                }
                process.waitFor(10L, TimeUnit.SECONDS)
            }

            if (filesMap.size == localesMap.size) {
                localesMap.forEach { (folder, _) ->
                    val stringsFile = File("${parameters.parentFolder.get()}/$folder/strings.xml")
                    stringsFile.writeText(filesMap[folder].toString())
                }

                logger.debug("All translations downloaded")
            } else {
                throw GradleException("Error downloading strings.xml")
            }
        }

        private fun buildTranslationRequestUrl(currentLocale: String): String = "https://www.transifex.com/api/2/" +
                "project/" +
                "${parameters.projectSlug.get()}/" +
                "resource/" +
                "${parameters.resourceSlug.get()}/" +
                "translation/" +
                "$currentLocale?mode=default&file=xml"
    }
}

package co.thebeat.localization

import co.thebeat.localization.extensions.TransifexLocalizationExtension
import co.thebeat.localization.tasks.DownloadLocalizationsTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/**
 * Created by Chris Margonis on 17/07/2018.
 */
open class DownloadLocalizationsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val transifexExt = project
                .extensions
                .create<TransifexLocalizationExtension>(TransifexLocalizationExtension.NAME)
        project.tasks.register(DownloadLocalizationsTask.NAME, DownloadLocalizationsTask::class.java, transifexExt)
    }
}

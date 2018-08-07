package com.beat.localization

import com.beat.localization.extensions.TransifexLocalizationExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Chris Margonis on 17/07/2018.
 */
open class DownloadLocalizationsPlugin : Plugin<Project> {

    override fun apply(project: Project?) {
        if (project == null) return

        with(project) {
            val transifexExtension = extensions.create(
                "transifexLocalization",
                TransifexLocalizationExtension::class.java
            )
            task("fetchLocalization")
                .doLast {
                    fetchLocalizations(transifexExtension)
                }
        }
    }

    /**
     * Gets the localization files for each extension loaded.
     */
    private fun fetchLocalizations(vararg extension: LocalizationExtension?) {
        extension.forEach {
            it?.execute() ?: throw GradleException("Invalid configuration loaded")
        }
    }
}
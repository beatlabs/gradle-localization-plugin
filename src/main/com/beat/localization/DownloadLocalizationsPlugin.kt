package com.beat.localization

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Chris Margonis on 17/07/2018.
 */
open class DownloadLocalizationsPlugin : Plugin<Project> {

    override fun apply(p: Project?) {
        p?.let {
            with(p) {
                task("helloWorld")
                    .doLast { System.out.println("Sample output, kotlin plugin") }
            }
        }
    }
}
package com.beat.localization

/**
 * Created by Chris Margonis on 18/07/2018.
 */
open class TransifexLocalizationExtension : LocalizationConfiguration {
    override lateinit var auth: String
    override lateinit var resource: String
    override lateinit var localesMap: Map<String, String>
    override lateinit var srcDir: String
}

interface LocalizationConfiguration {
    var auth: String
    var resource: String
    var localesMap: Map<String, String>
    var srcDir: String
}
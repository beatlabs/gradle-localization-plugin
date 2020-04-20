package co.thebeat.localization.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

@Suppress("UnstableApiUsage")
open class TransifexLocalizationExtension @JvmOverloads constructor(
        // Needed for Gradle
        @get:Internal
        internal val name: String = "default",
        objects: ObjectFactory
) {

    companion object {
        const val NAME = "transifexLocalization"
    }

    @get:Input
    val apiToken: Property<String> = objects.property(String::class.java)

    @get:Input
    val resourceSlug: Property<String> = objects.property(String::class.java)

    @get:Input
    val localesMap: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java)

    @get:Input
    val srcDir: Property<String> = objects.property(String::class.java)

    @get:Input
    val projectSlug: Property<String> = objects.property(String::class.java)
}

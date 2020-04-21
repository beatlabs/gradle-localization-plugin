## gradle-localization-plugin
![Continuous Integration](https://github.com/beatlabs/gradle-localization-plugin/workflows/Continuous%20Integration/badge.svg?branch=develop)

Gradle plugin for automation regarding string downloading in Android apps.
The plugin in its current state has been integrated with Transifex and uses [Transifex API v2](https://docs.transifex.com/api/introduction).

## Instructions

### Apply the plugin

Using [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block)
```
plugins {
  id "co.thebeat.localization" version "<version>"
}

```

Using [legacy plugin application](https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application)
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.co.thebeat.localization:localization-plugin:<version>"
  }
}

apply plugin: "co.thebeat.localization"
```

### Configuring the plugin

Add this configuration in your `app` `build.gradle` file (does not need to be inside another closure)

**groovy**:

```groovy
transifexLocalization {
    transifexAPIToken = 'your-api-key'
    transifexResourceSlug = 'your-resource-path-here'
    transifexProjectSlug = 'your-project-name-here'
    localesMap = [:]
    // example localization matchings
    localesMap['main/res/values'] = 'en'
    localesMap['greece/res/values'] = 'el_GR'
    localesMap['colombia/res/values'] = 'es_CO'
    localesMap['chile/res/values'] = 'es_CL'
    srcDir = "${projectDir}/src"
}
```

**gradle kotlin dsl**:

```kotlin
configure<co.thebeat.localization.extensions.TransifexLocalizationExtension> {
    transifexAPIToken = "your-api-key"
    transifexResourceSlug = "your-resource-path-here"
    transifexProjectSlug = "your-project-name-here"
    localesMap = HashMap<String, String>().apply {
        // example localization matchings
        this["main/res/values"] = "en"
        this["greece/res/values"] = "el_GR"
        this["colombia/res/values"] = "es_CO"
        this["chile/res/values"] = "es_CL"
    }
    srcDir = "${projectDir}/src"
}
```
- Execute `./gradlew fetchLocalization`

## Contributing

Please consult the [Contribution guidelines](CONTRIBUTING.md).

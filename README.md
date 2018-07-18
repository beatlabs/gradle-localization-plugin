# gradle-localization-plugin
Gradle plugin for automation regarding strings download in Android apps

# Instructions

- Get the jar (or build it by running `gradlew clean build jar`)
- Place it in the `{project_dir/libs/}` fodler
- Add this in the project's `build.gradle`
```
buildScript {
  repositories {
        //...
        flatDir { dirs 'libs' }
    }
    dependencies {
    // replace 0.0.1-SNAPSHOT with your plugin version
    classpath "com.beat.localization:gradle-localization-plugin:0.0.1-SNAPSHOT"
    }
}

apply plugin: "com.beat.localization"
```
- Add this configuration in your `app` `build.gradle` file (does not need to be inside another closure)
```
transifexLocalization {
    auth = 'api-key'
    resource = 'android-driver|android-passenger'
    localesMap = [:]
    localesMap['main/res/values'] = 'en'
    localesMap['greece/res/values'] = 'el_GR'
    localesMap['peru/res/values'] = 'es_MX'
    localesMap['colombia/res/values'] = 'es_CO'
    localesMap['chile/res/values'] = 'es_CL'
    srcDir = "${projectDir}/src"
}
```
- Execute `./gradlew fetchLocalization`

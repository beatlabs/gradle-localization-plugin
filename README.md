## gradle-localization-plugin
Gradle plugin for automation regarding string downloading in Android apps

## Instructions

- Get the jar (or build it by running `cd localization-plugin; ./gradlew clean build jar`)
- Place it in the `{project_dir/libs/}` folder
- Add this in the project's `build.gradle`
```
buildScript {
  repositories {
        //...
        flatDir { dirs 'libs' }
    }
    dependencies {
    // replace 0.0.1-SNAPSHOT with your plugin version
    classpath "co.thebeat.localization:gradle-localization-plugin:0.0.1-SNAPSHOT"
    }
}

```
- Add this configuration in your `app` `build.gradle` file (does not need to be inside another closure)
```
apply plugin: "co.thebeat.localization"

transifexLocalization {
    auth = 'your-api-key'
    resourceSlug = 'your-resource-path-here'
    projectSlug = 'your-project-name-here'
    localesMap = [:]
    // example localization matchings
    localesMap['main/res/values'] = 'en'
    localesMap['greece/res/values'] = 'el_GR'
    localesMap['colombia/res/values'] = 'es_CO'
    localesMap['chile/res/values'] = 'es_CL'
    srcDir = "${projectDir}/src"
}
```
- Execute `./gradlew fetchLocalization`

## Contributing

Please consult the [Contribution guidelines](CONTRIBUTE.md).

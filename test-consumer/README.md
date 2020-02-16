# Consumer project

This gradle project has the purpose of performing
manual tests while developing the localization plugin.

It's a composite build that includes **localization-plugin** in
its _settings.gradle.kts_ file.

To run it put your api settings as instructed in `gradle-example.properties` file,
and at the command line execute:
```
$> cd <here>
$> ./gradlew fetchLocalization

```
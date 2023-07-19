import com.ivy.buildsrc.*

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    api(project(":core:data-model"))
    api(project(":resources"))

    Hilt()
    Kotlin(api = true)
    Coroutines(api = true)
    FunctionalProgramming(api = true)
    Timber(api = true)

    Testing(
        // Prevent circular dependency
        commonTest = false,
        commonAndroidTest = false
    )
}
android {
    namespace = "com.ivy.common"
}

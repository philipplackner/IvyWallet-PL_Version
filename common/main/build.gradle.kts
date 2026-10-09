import com.ivy.buildsrc.*

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
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

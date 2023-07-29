import com.ivy.buildsrc.ComposeTesting
import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.FunctionalProgramming
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Lifecycle
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:persistence"))
    implementation(project(":core:exchange-provider"))

    FunctionalProgramming(false)
    Lifecycle(api = false)
    ComposeTesting(api = false) // for IdlingResource
    Testing()
    Coroutines(api = false)
}
android {
    namespace = "com.ivy.core.domain"
}

import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":design-system"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
    implementation(project(":core:persistence"))
    implementation(project(":common:main"))
    Testing()
    Coroutines(api = false)
}
android {
    namespace = "com.ivy.exchangeRates"
}

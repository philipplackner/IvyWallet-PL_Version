import com.ivy.buildsrc.Billing
import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    `kotlin-android`
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))

    implementation(project(":core:data-model"))
    implementation(project(":core:ui"))

    Billing(api = true)
}
android {
    namespace = "com.ivy.billing"
}

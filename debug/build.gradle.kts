import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    implementation(project(":common:main"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":navigation"))
    implementation(project(":design-system"))
    Hilt()
}
android {
    namespace = "com.ivy.debug"
}

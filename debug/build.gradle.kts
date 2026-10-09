import com.ivy.buildsrc.Hilt

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
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

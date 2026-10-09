import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))

    implementation(project(":design-system"))
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
}
android {
    namespace = "com.ivy.locked"
}

import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"

}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:ui"))
    implementation(project(":design-system"))
    implementation(project(":formula:domain"))
}
android {
    namespace = "com.ivy.formula.ui"
}

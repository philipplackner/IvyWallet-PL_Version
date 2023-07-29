import com.ivy.buildsrc.Hilt

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"

}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":common:android-test"))
    implementation(project(":design-system"))
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
    implementation(project(":navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:persistence"))

    implementation(project(":main:bottom-bar"))
    implementation(project(":main:customer-journey"))
}
android {
    namespace = "com.ivy.home"
}

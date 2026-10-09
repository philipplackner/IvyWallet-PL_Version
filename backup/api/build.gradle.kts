import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":design-system"))
    implementation(project(":backup:base"))
    implementation(project(":backup:old"))
    implementation(project(":backup:impl"))
    Testing()
}
android {
    namespace = "com.ivy.backup.api"
}

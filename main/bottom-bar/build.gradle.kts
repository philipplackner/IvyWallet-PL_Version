import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"

}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":design-system"))
    implementation(project(":core:ui"))
    implementation(project(":navigation"))
    implementation(project(":resources"))
    Testing()
}
android {
    namespace = "com.ivy.main.bottombar"
}

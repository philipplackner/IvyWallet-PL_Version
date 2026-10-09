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
    implementation(project(":design-system"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
    implementation(project(":navigation"))
    Testing()
}
android {
    namespace = "com.ivy.categories"
}

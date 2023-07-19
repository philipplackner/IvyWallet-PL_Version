import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"

}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":design-system"))
    implementation(project(":core:data-model"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":navigation"))
    implementation(project(":main:bottom-bar"))
    Testing()
}
android {
    namespace = "com.ivy.accounts"
}

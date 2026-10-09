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
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":android:file-system"))
    implementation(project(":design-system"))
    Testing()
}
android {
    namespace = "com.ivy.photo.frame"
}

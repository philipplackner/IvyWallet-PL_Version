import com.ivy.buildsrc.AndroidX
import com.ivy.buildsrc.Hilt


plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:ui"))
    AndroidX(api = false)
}
android {
    namespace = "com.ivy.notifications"
}

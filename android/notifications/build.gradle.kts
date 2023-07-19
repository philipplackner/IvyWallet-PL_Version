import com.ivy.buildsrc.AndroidX
import com.ivy.buildsrc.Hilt


plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
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

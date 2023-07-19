import com.ivy.buildsrc.Compose
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
    Compose(api = false)
}
android {
    namespace = "com.ivy.navigation"
}

import com.ivy.buildsrc.Compose
import com.ivy.buildsrc.Hilt

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"

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

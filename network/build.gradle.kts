import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Ktor

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    Ktor(api = true)
}
android {
    namespace = "com.ivy.network"
}

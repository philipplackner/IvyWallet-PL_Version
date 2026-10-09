import com.ivy.buildsrc.AssertK
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.JUnit5

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":parser"))
}
android {
    namespace = "com.ivy.math"
}

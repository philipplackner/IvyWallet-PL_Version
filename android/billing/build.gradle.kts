import com.ivy.buildsrc.Billing
import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))

    implementation(project(":core:data-model"))
    implementation(project(":core:ui"))

    Billing(api = true)
}
android {
    namespace = "com.ivy.billing"
}

import com.ivy.buildsrc.DataStore
import com.ivy.buildsrc.Glance
import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    id("de.mannodermaus.android-junit5") version "2.0.1"
}
apply<com.ivy.buildsrc.IvyComposePlugin>()

android {
    buildFeatures {
        compose = true
    }
    namespace = "com.ivy.widgets"
}

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":design-system"))
    implementation(project(":core:data-model"))
    implementation(project(":core:ui"))
    Glance()

    DataStore(api = false)
}
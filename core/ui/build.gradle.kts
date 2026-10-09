import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.RoomDB
import com.ivy.buildsrc.Testing

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":design-system"))
    implementation(project(":core:domain"))
    implementation(project(":core:persistence"))
    implementation(project(":navigation"))
    implementation(project(":math"))
    RoomDB(api = false)
    Coroutines(api = false)
    Testing()
}
android {
    namespace = "com.ivy.core.ui"
}

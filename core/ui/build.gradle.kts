import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.RoomDB
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
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

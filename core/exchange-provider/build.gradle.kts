import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Networking
import com.ivy.buildsrc.RoomDB
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    id("dagger.hilt.android.plugin")

    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:data-model"))
    implementation(project(":network"))
    RoomDB(api = true)
    Networking(api = false)

    Testing()
}
android {
    namespace = "com.ivy.exchange"
}

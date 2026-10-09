import com.ivy.buildsrc.AppCompat
import com.ivy.buildsrc.Hilt

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    AppCompat(api = false)
}
android {
    namespace = "com.ivy.resources"
}

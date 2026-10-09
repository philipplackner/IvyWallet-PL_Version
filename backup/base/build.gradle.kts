import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:domain"))
    implementation(project(":android:file-system"))
    Testing()
}
android {
    namespace = "com.ivy.backup.base"
}

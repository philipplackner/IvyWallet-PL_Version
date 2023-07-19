import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:domain"))
    implementation(project(":core:data-model"))
    implementation(project(":core:persistence"))
    api(project(":backup:base"))
    implementation(project(":android:file-system"))
    Testing()
}
android {
    namespace = "com.ivy.backup.old"
}

import com.ivy.buildsrc.AppCompat
import com.ivy.buildsrc.Hilt
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
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
    implementation(project(":core:persistence"))
    implementation(project(":navigation"))
    implementation(project(":backup:old"))
    implementation(project(":drive:google-drive"))
    implementation(project(":backup:impl"))
    AppCompat(false)
    Testing()


}
android {
    namespace = "com.ivy.settings"
}

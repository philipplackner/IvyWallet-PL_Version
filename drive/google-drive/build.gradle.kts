import com.ivy.buildsrc.Google
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing
import com.ivy.buildsrc.Timber

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    implementation(project(":common:main"))
    implementation(project(":android:common"))

    // region Google Drive deps
    // TODO: Extract to "dependencies.gradle.kts" in buildSrc
    implementation("com.google.apis:google-api-services-drive:v3-rev136-1.25.0") {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    implementation("com.google.http-client:google-http-client-gson:1.26.0")
    implementation("com.google.api-client:google-api-client-android:1.26.0") {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    implementation("com.google.guava:guava:28.1-android")
    // endregion

    Hilt()
    Google()
    Timber(api = true)
    Testing()
}
android {
    namespace = "com.ivy.drive.google_drive"
}

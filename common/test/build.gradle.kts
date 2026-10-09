import com.ivy.buildsrc.AssertK
import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.JUnit5
import com.ivy.buildsrc.Kotlin
import com.ivy.buildsrc.Testing

plugins {
    `android-library`

    id("de.mannodermaus.android-junit5") version "2.0.1"
}

apply<com.ivy.buildsrc.IvyTestPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:domain"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${com.ivy.buildsrc.Versions.junitJupiter}")
    Testing(
        // Prevent circular dependency
        commonTest = false,
        commonAndroidTest = false
    )
    Kotlin(api = false)
    Coroutines(api = false)
}
android {
    namespace = "com.ivy.common.test"
}

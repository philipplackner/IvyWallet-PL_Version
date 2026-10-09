plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    //https://mvnrepository.com/artifact/com.android.tools.build/gradle?repo=google
    implementation("com.android.tools.build:gradle:9.3.3")

    //https://kotlinlang.org/docs/releases.html#release-details
    // Must match kotlinVersion from dependencies.kt
    val kotlinVersion = "2.4.21"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation(kotlin("serialization", version = kotlinVersion))
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:$kotlinVersion")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.3.12")

    //https://developer.android.com/training/dependency-injection/hilt-android
    // Must match hiltVersion from dependencies.kt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.60.1")

    //URL: https://developers.google.com/android/guides/google-services-plugin
    implementation("com.google.gms:google-services:4.5.0")

    //https://www.mongodb.com/docs/realm/sdk/kotlin/install/android/
    // Must match Versions.realm from dependencies.kt
//    implementation("io.realm.kotlin:gradle-plugin:1.16.0")

    implementation("com.google.firebase:firebase-crashlytics-gradle:3.0.8")
}
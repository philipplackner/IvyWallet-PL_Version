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
    implementation("com.android.tools.build:gradle:8.5.2")

    //https://kotlinlang.org/docs/releases.html#release-details
    // Must match kotlinVersion from dependencies.kt
    // Warning: KSP must match Kotlin's version
    val kotlinVersion = "1.9.25"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation(kotlin("serialization", version = kotlinVersion))
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.25-1.0.20")

    //https://developer.android.com/training/dependency-injection/hilt-android
    // Must match hiltVersion from dependencies.kt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.52")

    //URL: https://developers.google.com/android/guides/google-services-plugin
    implementation("com.google.gms:google-services:4.4.2")

    //https://www.mongodb.com/docs/realm/sdk/kotlin/install/android/
    // Must match Versions.realm from dependencies.kt
//    implementation("io.realm.kotlin:gradle-plugin:1.16.0")

    implementation("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
}
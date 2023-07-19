import com.ivy.buildsrc.AndroidX
import com.ivy.buildsrc.Compose
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Lifecycle

plugins {
    `android-library`
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

android {
    defaultConfig {
        testInstrumentationRunner = "com.ivy.common.androidtest.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = com.ivy.buildsrc.Versions.composeCompilerVersion
    }

    packagingOptions {
        //Exclude this files so Jetpack Compose UI tests can build
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        //-------------------------------------------------------
    }
    namespace = "com.ivy.design"
}

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:data-model"))
    implementation(project(":resources"))

    Compose(api = true)
    AndroidX(api = true)
    Lifecycle(api = true)
}
package com.ivy.buildsrc

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class IvyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        configureJavaToolchain(project)
        addKotlinCompilerArgs(project)
        configureCompose(project)
        setProjectSdkVersions(project)

        test(project)
        // Gradle 9 fails test tasks with no tests; most modules have none but AGP still wires test inputs.
        project.tasks.withType<Test>().configureEach { failOnNoDiscoveredTests.set(false) }
        androidTest(project)
        excludeDuplicateMetaInf(project)
        lint(project)
        kspSourceSets(project)
    }

//    private fun robolectric(project: Project) {
//        project.androidLibrary().testOptions {
//            unitTests.isIncludeAndroidResources = true
//        }
//    }

    protected open fun test(project: Project) {
        project.dependencies {
            "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
            testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
            testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
            testImplementation("com.willowtreeapps.assertk:assertk:${Versions.assertK}")
            androidTestImplementation("com.willowtreeapps.assertk:assertk:${Versions.assertK}")
            testImplementation("io.mockk:mockk:${Versions.mockk}")
            androidTestImplementation("io.mockk:mockk-android:${Versions.mockk}")
        }
    }

    protected open fun androidTest(project: Project) {
        project.androidLibrary().testOptions {
            unitTests.isIncludeAndroidResources = true
        }
    }

    private fun excludeDuplicateMetaInf(project: Project) {
        project.androidLibrary().packaging {
            resources.excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1", "META-INF/DEPENDENCIES"))
        }
    }

    /**
     * Global lint configuration
     */
    private fun lint(project: Project) {
        project.androidLibrary().lint {
            disable.add("MissingTranslation")
        }
    }

    private fun configureJavaToolchain(project: Project) {
        // Configure Java toolchain to work with newer Gradle and Java versions
        project.extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin("android-library")
            plugin("org.jetbrains.kotlin.plugin.compose")
            plugin("com.google.devtools.ksp")
            plugin("dagger.hilt.android.plugin")

            //TODO: Enable if we migrate to kotlinx serialization
    //            plugin("kotlinx-serialization")
        }
    }

    private fun kspSourceSets(project: Project) {
        // KSP automatically handles source sets for each variant
        // No manual configuration needed
    }

    private fun addKotlinCompilerArgs(project: Project) {
        project.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
                freeCompilerArgs.add("-Xskip-prerelease-check")
            }
        }
    }

    private fun configureCompose(project: Project) {
        project.androidLibrary().compileOptions {
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
        }
        
        project.androidLibrary().buildFeatures {
            compose = true
        }
    }

    private fun setProjectSdkVersions(project: Project) {
        val library = project.androidLibrary()
        library.compileSdk = com.ivy.buildsrc.Project.compileSdkVersion
        library.defaultConfig {
            minSdk = com.ivy.buildsrc.Project.minSdk
        }
        library.testOptions {
            targetSdk = com.ivy.buildsrc.Project.targetSdk
        }
    }

    protected fun Project.androidLibrary(): LibraryExtension =
        extensions.getByType(LibraryExtension::class.java)
}
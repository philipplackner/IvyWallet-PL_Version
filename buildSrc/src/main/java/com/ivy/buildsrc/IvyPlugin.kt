package com.ivy.buildsrc

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class IvyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        addKotlinCompilerArgs(project)
        setProjectSdkVersions(project)

        test(project)
        androidTest(project)
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

    /**
     * Global lint configuration
     */
    private fun lint(project: Project) {
        project.androidLibrary().lint {
            disable.add("MissingTranslation")
        }
    }

    private fun androidTest(project: Project) {
        project.dependencies {
            androidTestImplementation("com.willowtreeapps.assertk:assertk:${Versions.assertK}")
            androidTestImplementation("io.mockk:mockk-android:${Versions.mockk}")
        }
        project.configurations.getByName("androidTestImplementation") {
            exclude(group = "io.mockk", module = "mockk-agent-jvm")
        }
        project.androidLibrary().defaultConfig {
            testInstrumentationRunner = "com.ivy.common.androidtest.HiltTestRunner"
        }
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin("android-library")
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("dagger.hilt.android.plugin")
            plugin("com.google.devtools.ksp")

            //TODO: Enable if we migrate to kotlinx serialization
    //            plugin("kotlinx-serialization")
        }
    }

    private fun kspSourceSets(project: Project) {
        project.androidLibrary().sourceSets {
            getByName("main").apply {
                java.srcDir("build/generated/ksp/debug")
                kotlin.srcDir("build/generated/ksp/debug")
                java.srcDir("build/generated/ksp/demo")
                kotlin.srcDir("build/generated/ksp/demo")
            }
            getByName("test").apply {
                java.srcDir("build/generated/ksp/debug")
                kotlin.srcDir("build/generated/ksp/debug")
                java.srcDir("build/generated/ksp/demo")
                kotlin.srcDir("build/generated/ksp/demo")
            }
        }
    }

    private fun addKotlinCompilerArgs(project: Project) {
        project.allprojects {
            allprojects {
                tasks.withType(KotlinCompile::class).all {
                    with(kotlinOptions) {
                        freeCompilerArgs = freeCompilerArgs + listOf("-Xcontext-receivers")
                        //Suppress Jetpack Compose versions compiler incompatibility, do NOT do it
//                        freeCompilerArgs = freeCompilerArgs + listOf(
//                            "-P",
//                            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
//                        )
                        freeCompilerArgs = freeCompilerArgs + listOf("-Xskip-prerelease-check")
                    }
                }
            }
        }
    }

    private fun setProjectSdkVersions(project: Project) {
        val library = project.androidLibrary()
        library.compileSdk = com.ivy.buildsrc.Project.compileSdkVersion
        library.defaultConfig {
            minSdk = com.ivy.buildsrc.Project.minSdk
            targetSdk = com.ivy.buildsrc.Project.targetSdk
        }
    }

    protected fun Project.androidLibrary(): LibraryExtension =
        extensions.getByType(LibraryExtension::class.java)
}
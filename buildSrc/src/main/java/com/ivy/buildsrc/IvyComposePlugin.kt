package com.ivy.buildsrc

import org.gradle.api.Project

abstract class IvyComposePlugin : IvyPlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        project.dependencies.add(
            "debugImplementation",
            "androidx.compose.ui:ui-test-manifest:${Versions.composeTestingManifest}"
        )

        val library = project.androidLibrary()
        library.composeOptions {
            kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
        }
        library.buildFeatures {
            compose = true
        }

    }
}
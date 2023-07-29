package com.ivy.buildsrc

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class IvyTestPlugin: IvyPlugin() {

    override fun test(project: Project) {
        project.dependencies {
            implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
            implementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
            implementation("com.willowtreeapps.assertk:assertk:${Versions.assertK}")
            implementation("io.mockk:mockk:${Versions.mockk}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
            implementation("app.cash.turbine:turbine:${Versions.turbine}")
        }
    }
}
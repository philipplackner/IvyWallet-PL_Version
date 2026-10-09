import com.ivy.buildsrc.DataStore
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.RoomDB

plugins {
    `android-library`
    id("com.google.devtools.ksp") // for Room DB

    id("de.mannodermaus.android-junit5") version "2.0.1"

}

apply<com.ivy.buildsrc.IvyPlugin>()

android {
    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/../room-db-schemas")
        }
    }
    namespace = "com.ivy.formula.persistence"
}

dependencies {
    Hilt()
    implementation(project(":common:main"))
    implementation(project(":core:domain"))
    implementation(project(":core:persistence"))
    RoomDB(api = false)
    DataStore(api = false)
}
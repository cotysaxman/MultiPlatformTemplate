val ktorVersion = project.properties["ktor_version"] as String

plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":client:common:ktor-client:dsl"))
                implementation(ktorClientDependency("js", ktorVersion))
            }
        }
    }
}

fun ktorClientDependency(
    simpleName: String,
    ktorVersion: String
) = "io.ktor:ktor-client-$simpleName:$ktorVersion"

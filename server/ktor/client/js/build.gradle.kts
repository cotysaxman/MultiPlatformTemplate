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
                api(project(":server:ktor:configuration:client-utils"))
                implementation(ktorClientDependency("js", ktorVersion))
            }
        }
    }
}

fun ktorClientDependency(
    simpleName: String,
    ktorVersion: String
) = "io.ktor:ktor-client-$simpleName:$ktorVersion"

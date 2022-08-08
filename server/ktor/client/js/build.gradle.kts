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
                implementation(project(":server:ktor:configuration"))
                implementation(project(":server:ktor:configuration:client-utils"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

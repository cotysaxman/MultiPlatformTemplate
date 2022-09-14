import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser {
            webpackTask {

            }
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        named("jsMain") {
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation(project(":client:common:ktor-client:js"))
                implementation(compose.runtime)
                implementation(compose.web.core)
            }
        }
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

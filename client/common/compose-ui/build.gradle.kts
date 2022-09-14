import org.jetbrains.compose.compose

plugins {
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":common:platform-utils"))
                api(project(":client:common:ktor-client:cio"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(compose.uiTooling)
            }
        }

        val androidMain by getting {
            dependencies {
                val appCompatVersion: String by project
                val composeAndroidxVersion: String by project

                api("androidx.appcompat:appcompat:$appCompatVersion")
                api("androidx.core:core-ktx:1.8.0")
                api("androidx.compose.runtime:runtime:$composeAndroidxVersion")
                api("androidx.compose.foundation:foundation:$composeAndroidxVersion")
                api("androidx.compose.material:material:$composeAndroidxVersion")
                api("androidx.compose.ui:ui:$composeAndroidxVersion")
                implementation("androidx.compose.ui:ui-tooling:$composeAndroidxVersion")
            }
        }
    }
}

android {
    compileSdk = 32
    @Suppress("UnstableApiUsage")
    buildToolsVersion = "33.0.0"
    @Suppress("UnstableApiUsage")
    defaultConfig {
        minSdk = 28
        targetSdk = 31
    }

    @Suppress("UnstableApiUsage")
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/resources")
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    @Suppress("UnstableApiUsage")
    composeOptions {
        val composeCompilerVersion: String by project
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

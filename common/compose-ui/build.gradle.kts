import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

dependencies {
    debugImplementation(compose.uiTooling)
    implementation(compose.preview)
}

android {
    compileSdk = 31
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
}

kotlin {
    jvm("desktop")
    android()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }

        named("androidMain") {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.4.2")
                implementation("androidx.core:core-ktx:1.8.0")
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}

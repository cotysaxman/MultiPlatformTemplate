plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain")
        named("androidMain")
        named("desktopMain")
        named("jsMain")
    }
}

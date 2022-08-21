import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
}

kotlin {
    jvm()
    android()
    js(IR) {
        browser()
    }
    nativeTarget("native")
}

fun KotlinMultiplatformExtension.nativeTarget(name: String) = when (System.getProperty("os.name")) {
    "Mac OS X" -> macosX64(name)
    "Linux" -> linuxX64(name)
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
}

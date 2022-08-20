import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val ktorVersion = project.properties["ktor_version"] as String

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
    jvm()
    android()
    nativeTarget("native")

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation(project(":server:ktor:configuration"))
                implementation(project(":server:ktor:configuration:client-utils"))
                implementation(ktorClientDependency("core", ktorVersion))
                implementation(ktorClientDependency("cio", ktorVersion))
            }
        }
    }
}

fun ktorClientDependency(
    simpleName: String,
    ktorVersion: String
) = "io.ktor:ktor-client-$simpleName:$ktorVersion"

fun KotlinMultiplatformExtension.nativeTarget(name: String) = when (System.getProperty("os.name")) {
    "Mac OS X" -> macosX64(name)
    "Linux" -> linuxX64(name)
    // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val ktorVersion = project.properties["ktor_version"] as String

plugins {
    kotlin("multiplatform")
    application
}

kotlin {
    jvm()
    nativeTarget("native")

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation(project(":server:ktor:configuration"))
                implementation(ktorServerDependency("core", ktorVersion))
            }
        }
    }
}

fun ktorServerDependency(
    simpleName: String,
    ktorVersion: String
) = "io.ktor:ktor-server-$simpleName:$ktorVersion"

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

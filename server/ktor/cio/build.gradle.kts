import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val kotlinVersion: String by project
val ktorVersion = project.properties["ktor_version"] as String
val logbackVersion = project.properties["logback_version"] as String

plugins {
    kotlin("multiplatform")
    application
}

(project.extensions.getByName("application") as JavaApplication).apply {
    mainClass.set("com.exawizards.multiplatform_template.server.ktor.cio.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    jvm {
        withJava()
    }
    nativeTarget("native").apply {
        binaries {
            executable {
                entryPoint = "com.exawizards.multiplatform_template.server.ktor.cio.main"
            }
        }
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation(project(":common:platform-utils"))
                implementation(project(":server:ktor:configuration"))
                implementation(project(":server:ktor:configuration:server-utils"))
                implementation(ktorServerDependency("core", ktorVersion))
                implementation(ktorServerDependency("cio", ktorVersion))
                implementation(ktorServerDependency("cors", ktorVersion))
            }
        }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(ktorServerDependency("tests", ktorVersion))
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

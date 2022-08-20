import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val kotlinVersion: String by project
val ktorVersion = project.properties["ktor_version"] as String
val logbackVersion = project.properties["logback_version"] as String
val serializationJsonVersion: String by project

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
        val commonMain by getting
        commonMain.dependencies {
            implementation("ch.qos.logback:logback-classic:$logbackVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationJsonVersion")
            implementation(project(":common:platform-utils"))
            implementation(project(":server:ktor:configuration"))
            implementation(project(":server:ktor:configuration:server-utils"))
            implementation(ktorServerDependency("core", ktorVersion))
            implementation(ktorServerDependency("cio", ktorVersion))
            implementation(ktorServerDependency("cors", ktorVersion))
            implementation(ktorServerDependency("content-negotiation", ktorVersion))
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(ktorServerDependency("test-host", ktorVersion))
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

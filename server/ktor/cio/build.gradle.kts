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

    nativeTarget("shared") // decoy target!

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        named("commonTest")
        named("nativeMain") {
            val nativeMainPath = "build/src/sharedMain/native"
            getSharedMainPath().copyRecursively(mkdir(nativeMainPath), overwrite = true)
            kotlin.srcDir(nativeMainPath)
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")
            }
        }
        named("nativeTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-server-tests:$ktorVersion")
            }
        }
        named("jvmMain") {
            val jvmMainPath = "build/src/sharedMain/jvm"
            getSharedMainPath().copyRecursively(mkdir(jvmMainPath), overwrite = true)
            kotlin.srcDir(jvmMainPath)
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")
            }
        }
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
            }
        }

        named("sharedMain") {
            dependencies {
                implementation(project(":common:platform-utils"))
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")
            }
        }
    }
}

fun KotlinMultiplatformExtension.nativeTarget(name: String) = when (System.getProperty("os.name")) {
    "Mac OS X" -> macosX64(name)
    "Linux" -> linuxX64(name)
    // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
}

fun getSharedMainPath(): File {
    return project.file("src/sharedMain")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

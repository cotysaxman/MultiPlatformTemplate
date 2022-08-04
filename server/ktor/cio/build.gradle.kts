val kotlinVersion: String by project
val ktorVersion = project.properties["ktor_version"] as String
val logbackVersion = project.properties["logback_version"] as String

plugins {
    kotlin("multiplatform")
}

kotlin {
    val nativeTarget = when (System.getProperty("os.name")) {
        "Mac OS X" -> macosX64("native")
        "Linux" -> linuxX64("native")
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.exawizards.multiplatform_template.server.ktor.cio.main"
            }
        }
    }

    sourceSets {
        named("nativeMain") {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        named("nativeTest") {
            dependencies {
                implementation("io.ktor:ktor-server-tests:$ktorVersion")
            }
        }
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

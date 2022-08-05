val kotlinVersion: String by project
val ktorVersion = project.properties["ktor_version"] as String
val logbackVersion = project.properties["logback_version"] as String

plugins {
    kotlin("multiplatform")
    application
}

(project.extensions.getByName("application") as JavaApplication).apply {
    mainClass.set("ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    jvm {
        withJava()
    }

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
        named("commonMain") {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        named("commonTest")
        named("nativeMain") {
            dependencies {
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
            dependencies {
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
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

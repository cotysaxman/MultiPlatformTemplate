import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":common:compose-ui"))
                implementation(compose.desktop.currentOs)
            }
        }
        named("jvmTest")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MultiPlatformTemplate"
            packageVersion = "1.0.0"
        }
    }
}

plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common:compose-ui"))
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.appcompat:appcompat:1.4.2")

    debugImplementation(compose.uiTooling)
    implementation(compose.preview)
    implementation(compose.material)
}

android {
    compileSdk = 31
    @Suppress("UnstableApiUsage")
    buildToolsVersion = "33.0.0"
    @Suppress("UnstableApiUsage")
    defaultConfig {
        applicationId = "$group.android"
        minSdk = 28
        targetSdk = 31
        versionCode = 1
        versionName = version.toString()
    }
    @Suppress("UnstableApiUsage")
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    @Suppress("UnstableApiUsage")
    sourceSets {
        named("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            res.srcDirs("src/main/res")
        }
    }
}

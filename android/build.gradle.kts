plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
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

    @Suppress("UnstableApiUsage")
    composeOptions {
        val composeCompilerVersion: String by project
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    val activityComposeVersion: String by project
    val composeAndroidxVersion: String by project

    implementation(project(":common:compose-ui"))
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    implementation("androidx.compose.ui:ui-tooling:$composeAndroidxVersion")
}

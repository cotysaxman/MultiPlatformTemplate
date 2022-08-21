// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
pluginManagement {
    val kotlinVersion: String by settings
    val agpVersion: String by settings
    val composeVersion: String by settings

    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("android") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
        id("org.jetbrains.compose") version composeVersion
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}

rootProject.apply {
    name = "MultiPlatformTemplate"
}

include(
    ":common:platform-utils",
    ":common:compose-ui",
    ":android",
    ":desktop",
    ":web",
    ":server:ktor:cio",
    ":server:ktor:client:cio",
    ":server:ktor:client:js",
    ":server:ktor:configuration",
    ":server:ktor:configuration:client-utils",
    ":server:ktor:configuration:server-utils",
)

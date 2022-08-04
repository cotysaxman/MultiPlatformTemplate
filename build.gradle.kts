import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

allprojects {
    extra["group"] = "com.exawizards.multiplatform.template"
    extra["version"] = "0-PROTOTYPE"

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins.withType(NodeJsRootPlugin::class.java) {
        extensions.configure<NodeJsRootExtension> {
            versions.apply {
                webpack.version = webpackVersion
                webpackCli.version = webpackCliVersion
                webpackDevServer.version = webpackDevServerVersion
                sourceMapLoader.version = sourceMapLoaderVersion
            }
        }
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}

val webpackVersion: String by project
val webpackCliVersion: String by project
val webpackDevServerVersion: String by project
val sourceMapLoaderVersion: String by project

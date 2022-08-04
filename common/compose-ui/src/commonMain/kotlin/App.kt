package com.exawizards.multiplatform_template.compose_ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.exawizards.multiplatform_template.platform_utils.getPlatformName

@Composable
fun App() {
    Scaffold(
        topBar = { Text("Header") },
        bottomBar = { VersionLabel() }
    ) {
        Text("App Body")
    }
}

@Composable
fun VersionLabel() {
    val platformName = getPlatformName()
    Text(platformName)
}

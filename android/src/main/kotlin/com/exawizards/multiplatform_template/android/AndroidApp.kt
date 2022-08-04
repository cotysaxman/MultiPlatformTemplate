package com.exawizards.multiplatform_template.android

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exawizards.multiplatform_template.compose_ui.App

@Composable
fun AndroidApp() {
    MaterialTheme {
        App()
    }
}

@Preview
@Composable
private fun Preview_AndroidApp() {
    AndroidApp()
}

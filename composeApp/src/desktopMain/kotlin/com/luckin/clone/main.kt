package com.luckin.clone

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Luckin Coffee",
        state = rememberWindowState(width = 420.dp, height = 900.dp)
    ) {
        App()
    }
}

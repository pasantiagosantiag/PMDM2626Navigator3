package ies.sequeros.navigator3

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LoginNavigator3Example",
    ) {
        App()
    }
}
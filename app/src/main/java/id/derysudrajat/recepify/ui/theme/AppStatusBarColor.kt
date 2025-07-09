package id.derysudrajat.recepify.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

enum class StatusBarTheme {
    LIGHT, DARK
}

@Composable
fun AppStatusBarColor(color: Color = Color.Transparent, statusBarTheme: StatusBarTheme = StatusBarTheme.LIGHT) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = statusBarTheme == StatusBarTheme.LIGHT
        )
    }
}
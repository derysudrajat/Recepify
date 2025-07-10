package id.derysudrajat.recepify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.recepify.feature.MainRouter
import id.derysudrajat.recepify.ui.theme.RecepifyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecepifyTheme {
                MainRouter()
            }
        }
    }
}

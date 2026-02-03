package com.example.honestmatrimony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.honestmatrimony.ui.theme.HonestMatrimonyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            /* App-level dark mode state */
            var isDarkMode by remember { mutableStateOf(false) }

            HonestMatrimonyTheme(
                darkTheme = isDarkMode
            ) {
                StartScreen(
                    onToggleDarkMode = { enabled ->
                        isDarkMode = enabled
                    }
                )
            }
        }
    }
}

package com.elijah.transafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.elijah.transafe.navigation.AppNavHost
import com.elijah.transafe.ui.theme.TransafeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransafeTheme {
                AppNavHost()
            }
        }
    }
}

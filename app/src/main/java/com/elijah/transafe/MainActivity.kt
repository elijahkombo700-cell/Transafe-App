package com.elijah.transafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.elijah.transafe.navigation.*
import com.elijah.transafe.ui.theme.TransafeTheme
import com.elijah.transafe.ui.theme.screens.home.Homescreen
import com.elijah.transafe.ui.theme.screens.splash.Splashscreen
import com.elijah.transafe.ui.theme.screens.profile.Profilescreen
import com.elijah.transafe.ui.theme.screens.report.Reportscreen
import com.elijah.transafe.ui.theme.screens.tripsummary.TripSummaryscreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransafeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ROUTE_SPLASH) {
                    composable(ROUTE_SPLASH) {
                        Splashscreen(onNavigateNext = {
                            navController.navigate(ROUTE_HOME) {
                                popUpTo(ROUTE_SPLASH) { inclusive = true }
                            }
                        })
                    }
                    composable(ROUTE_HOME) {
                        Homescreen(navController)
                    }
                    composable(ROUTE_PROFILE) {
                        Profilescreen(navController)
                    }
                    composable(ROUTE_REPORT) {
                        Reportscreen(navController)
                    }
                    composable(ROUTE_TRIP_SUMMARY) {
                        TripSummaryscreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TransafeTheme {
        Greeting("Android")
    }
}
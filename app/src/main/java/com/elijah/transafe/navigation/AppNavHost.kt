package com.elijah.transafe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.ui.theme.screens.home.Homescreen
import com.elijah.transafe.ui.theme.screens.profile.Profilescreen
import com.elijah.transafe.ui.theme.screens.report.Reportscreen
import com.elijah.transafe.ui.theme.screens.splash.Splashscreen
import com.elijah.transafe.ui.theme.screens.tripsummary.TripSummaryscreen
import com.elijah.transafe.ui.theme.screens.dashboard.Dashboardscreen
import com.elijah.transafe.ui.theme.screens.tripactive.TripActivescreen
import com.elijah.transafe.ui.theme.screens.auth.Loginscreen
import com.elijah.transafe.ui.theme.screens.auth.Registerscreen
import com.elijah.transafe.data.AuthViewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUTE_SPLASH) {
            val context = LocalContext.current
            val authViewModel = AuthViewModel(navController, context)
            Splashscreen(onNavigateNext = {
                if (authViewModel.isUserLoggedIn()) {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                } else {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                }
            })
        }
        composable(ROUTE_LOGIN) {
            Loginscreen(navController = navController)
        }
        composable(ROUTE_REGISTER) {
            Registerscreen(navController = navController)
        }
        composable(ROUTE_HOME) {
            Homescreen(navController = navController)
        }
        composable(ROUTE_REPORT) {
            Reportscreen(navController = navController)
        }
        composable(ROUTE_PROFILE) {
            Profilescreen(navController = navController)
        }
        composable(ROUTE_TRIP_SUMMARY) {
            TripSummaryscreen(navController = navController)
        }
        composable(ROUTE_DASHBOARD) {
            Dashboardscreen(navController = navController)
        }
        composable(ROUTE_TRIP_ACTIVE) {
            TripActivescreen(navController = navController)
        }
    }
}

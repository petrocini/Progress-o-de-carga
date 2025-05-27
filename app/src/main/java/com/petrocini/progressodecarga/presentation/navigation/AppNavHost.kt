package com.petrocini.progressodecarga.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petrocini.progressodecarga.presentation.auth.AuthScreen
import com.petrocini.progressodecarga.presentation.home.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Auth.route) { AuthScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}

package com.petrocini.progressodecarga.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object AddWorkout : Screen("add_workout")
}

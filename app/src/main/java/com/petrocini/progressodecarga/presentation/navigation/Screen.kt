package com.petrocini.progressodecarga.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object AddWorkout : Screen("add_workout?exerciseName={exerciseName}") {
        fun withExercise(name: String): String {
            return "add_workout?exerciseName=${Uri.encode(name)}"
        }
    }
    object SelectExercise : Screen("select_exercise")
}

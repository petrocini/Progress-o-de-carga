package com.petrocini.progressodecarga.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.petrocini.progressodecarga.presentation.add.AddWorkoutScreen
import com.petrocini.progressodecarga.presentation.auth.AuthScreen
import com.petrocini.progressodecarga.presentation.home.HomeScreen
import com.petrocini.progressodecarga.presentation.home.HomeWrapper
import com.petrocini.progressodecarga.presentation.select_exercise.SelectExerciseScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val user = Firebase.auth.currentUser

    MainScaffold(
        navController = navController,
        user = user,
        onLogout = {
            Firebase.auth.signOut()
            navController.navigate("auth") { popUpTo(0) }
        },
        onDeleteAccount = {
            user?.delete()?.addOnCompleteListener {
                navController.navigate("auth") { popUpTo(0) }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Auth.route) {
                AuthScreen(navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.AddWorkout.route) {
                AddWorkoutScreen(navController)
            }
            composable(Screen.SelectExercise.route) {
                SelectExerciseScreen(navController) { selected ->
                    navController.navigate(Screen.AddWorkout.withExercise(selected.name))
                }
            }
        }
    }
}

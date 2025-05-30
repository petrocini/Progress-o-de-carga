package com.petrocini.progressodecarga.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.petrocini.progressodecarga.presentation.navigation.MainScaffold
import com.petrocini.progressodecarga.presentation.navigation.Screen

@Composable
fun HomeWrapper(navController: NavController) {
    val user = Firebase.auth.currentUser

    MainScaffold(
        navController = navController,
        user = user,
        onLogout = {
            Firebase.auth.signOut()
            navController.navigate(Screen.Auth.route) {
                popUpTo(0)
            }
        },
        onDeleteAccount = {
            user?.delete()?.addOnCompleteListener {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(0)
                }
            }
        }
    ) { padding ->
        HomeScreen(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}

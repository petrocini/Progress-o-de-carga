package com.petrocini.progressodecarga.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ✅ ESSENCIAL
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.presentation.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val workouts by viewModel.workouts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddWorkout.route)
            }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(workouts) { workout ->
                    WorkoutItem(workout)
                }
            }
        }
    }
}

@Composable
fun WorkoutItem(workout: Workout) {
    val time = remember(workout.timestamp) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(workout.timestamp))
    }

    val lastSet = workout.sets.lastOrNull()
    val setInfo = lastSet?.let { "${it.repetitions} reps • ${it.weight} kg" } ?: "No sets"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = workout.exercise, style = MaterialTheme.typography.titleMedium)
        Text(text = "$time - $setInfo", style = MaterialTheme.typography.bodyMedium)
    }
}

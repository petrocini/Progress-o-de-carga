package com.petrocini.progressodecarga.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.presentation.navigation.Screen
import com.petrocini.progressodecarga.presentation.commom.utils.formatDate
import com.petrocini.progressodecarga.presentation.components.DaySection

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val workouts by viewModel.workouts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
    }

    val selectedWorkouts = remember { mutableStateListOf<String>() }

    val groupedWorkouts = workouts.groupBy { formatDate(it.timestamp) }

    fun toggleSelection(workout: Workout) {
        if (selectedWorkouts.contains(workout.id)) {
            selectedWorkouts.remove(workout.id)
        } else {
            selectedWorkouts.add(workout.id)
        }
    }

    Scaffold(
        floatingActionButton = {
            if (selectedWorkouts.isEmpty()) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.SelectExercise.route)
                }) {
                    Text("+")
                }
            } else {
                FloatingActionButton(onClick = {
                    viewModel.deleteWorkouts(selectedWorkouts.toList())
                    selectedWorkouts.clear()
                }) {
                    Text("🗑")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            groupedWorkouts.forEach { (date, dayWorkouts) ->
                item {
                    DaySection(
                        date = date,
                        workouts = dayWorkouts,
                        selectedWorkouts = selectedWorkouts,
                        onSelect = { toggleSelection(it) }
                    )
                }
            }
        }
    }
}


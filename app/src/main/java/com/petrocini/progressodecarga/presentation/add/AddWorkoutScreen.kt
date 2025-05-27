package com.petrocini.progressodecarga.presentation.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.petrocini.progressodecarga.domain.model.WorkoutSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.petrocini.progressodecarga.data.remote.RetrofitService
import com.petrocini.progressodecarga.domain.model.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(navController: NavController) {
    var exerciseName by remember { mutableStateOf("") }
    var currentReps by remember { mutableStateOf("") }
    var currentWeight by remember { mutableStateOf("") }
    val sets = remember { mutableStateListOf<WorkoutSet>() }

    val isFormValid = exerciseName.isNotBlank() && sets.isNotEmpty()

    val allExercises by produceState(initialValue = emptyList<String>()) {
        value = try {
            RetrofitService.api.getExercises().results.map { it.name }
        } catch (e: Exception) {
            emptyList()
        }
    }

    val suggestions = allExercises.filter {
        it.contains(exerciseName, ignoreCase = true)
    }.take(5)


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("New Workout") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isFormValid) {
                        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@FloatingActionButton
                        val workout = Workout(
                            userId = userId,
                            exercise = exerciseName,
                            sets = sets.toList()
                        )

                        FirebaseFirestore.getInstance()
                            .collection("workouts")
                            .add(workout)
                            .addOnSuccessListener {
                                navController.popBackStack()
                            }
                    }
                }
            ) {
                Text("Save")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise name") },
                modifier = Modifier.fillMaxWidth()
            )

            Column {
                suggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { exerciseName = suggestion }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = currentReps,
                    onValueChange = { currentReps = it },
                    label = { Text("Reps") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = currentWeight,
                    onValueChange = { currentWeight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val reps = currentReps.toIntOrNull()
                    val weight = currentWeight.toFloatOrNull()
                    if (reps != null && weight != null) {
                        sets.add(WorkoutSet(reps, weight))
                        currentReps = ""
                        currentWeight = ""
                    }
                }
            ) {
                Text("Add Set")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Sets", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(sets) { set ->
                    Text("${set.repetitions} reps • ${set.weight} kg")
                }
            }
        }
    }
}

package com.petrocini.progressodecarga.presentation.add

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.petrocini.progressodecarga.domain.model.WorkoutSet
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.presentation.commom.utils.formatWeightInput
import com.petrocini.progressodecarga.presentation.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    navController: NavController,
    workoutToEdit: Workout? = null,
) {
    val context = LocalContext.current

    val viewModel: AddWorkoutViewModel = viewModel(
        factory = AddWorkoutViewModelFactory(context)
    )

    val exerciseNameFromArgs = navController
        .currentBackStackEntry
        ?.arguments
        ?.getString("exerciseName")

    LaunchedEffect(exerciseNameFromArgs) {
        if (!exerciseNameFromArgs.isNullOrBlank()) {
            viewModel.loadExercises()
            viewModel.setExerciseName(exerciseNameFromArgs)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init(workoutToEdit)
        viewModel.loadExercises()
    }

    val sets by viewModel.sets.collectAsState()

    var currentReps by remember { mutableStateOf("") }
    var currentWeight by remember { mutableStateOf("") }

    // Date picker state
    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(calendar.time) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val formattedDate = remember(selectedDate) { dateFormatter.format(selectedDate) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Novo treino") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@FloatingActionButton
                    viewModel.saveWorkout(userId) {
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Save")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            val selectedExercise by viewModel.selectedExercise.collectAsState()

            selectedExercise?.let { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate(Screen.SelectExercise.route)
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Exercício: ${exercise.name}", style = MaterialTheme.typography.titleMedium)
                        Text("Tipo: ${exercise.type}")
                        Text("Músculo: ${exercise.muscle}")
                        Text("Dificuldade: ${exercise.difficulty}")
                        Text("Instruções: ${exercise.instructions}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = formattedDate,
                    onValueChange = {},
                    label = { Text("Data do treino") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = false) {}
                )
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable {
                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    calendar.set(year, month, day)
                                    selectedDate = calendar.time
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = currentReps,
                    onValueChange = { input -> currentReps = input.filter { it.isDigit() } },
                    label = { Text("Repetições") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = currentWeight,
                    onValueChange = {
                        val digits = it.filter { it.isDigit() }
                        currentWeight = formatWeightInput(digits)
                    },
                    label = { Text("Carga (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val reps = currentReps.toIntOrNull()
                    val weight = currentWeight.replace(",", ".").toFloatOrNull()
                    if (reps != null && weight != null) {
                        viewModel.addSet(WorkoutSet(reps, weight))
                        currentReps = ""
                        currentWeight = ""
                    }
                }
            ) {
                Text("Adicionar série")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Séries", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(sets) { set ->
                    Text("${set.repetitions} reps • ${set.weight} kg")
                }
            }
        }
    }
}

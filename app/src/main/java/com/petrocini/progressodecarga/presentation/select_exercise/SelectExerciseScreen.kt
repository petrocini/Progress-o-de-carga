package com.petrocini.progressodecarga.presentation.select_exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.petrocini.progressodecarga.domain.model.Exercise
import com.petrocini.progressodecarga.presentation.components.ExerciseCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectExerciseScreen(
    navController: NavController,
    onExerciseSelected: (Exercise) -> Unit
) {
    val context = LocalContext.current
    val viewModel: SelectExerciseViewModel = viewModel(
        factory = SelectExerciseViewModelFactory(context)
    )

    val query = viewModel.query.collectAsState()
    val filteredExercises = viewModel.filteredExercises.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadExercises()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecionar exercício") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = query.value,
                onValueChange = viewModel::setQuery,
                label = { Text("Buscar por nome") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredExercises.value) { exercise ->
                    ExerciseCard(
                        name = exercise.name,
                        type = exercise.type,
                        muscle = exercise.muscle,
                        difficulty = exercise.difficulty,
                        onClick = { onExerciseSelected(exercise) }
                    )
                }
            }
        }
    }
}

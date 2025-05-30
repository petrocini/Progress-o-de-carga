package com.petrocini.progressodecarga.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petrocini.progressodecarga.domain.model.Workout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutCard(
    workout: Workout,
    isSelected: Boolean,
    onSelect: (Workout) -> Unit
) {
    val time = remember(workout.timestamp) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(workout.timestamp))
    }

    val lastSet = workout.sets.lastOrNull()
    val setInfo = lastSet?.let { "${it.repetitions} repetições • ${it.weight} kg" } ?: "Sem repetições"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onSelect(workout) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.secondaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = workout.exercise, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Última série: $setInfo", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Horário: $time", style = MaterialTheme.typography.labelSmall)
        }
    }
}


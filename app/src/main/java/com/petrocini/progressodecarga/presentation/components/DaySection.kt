package com.petrocini.progressodecarga.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.petrocini.progressodecarga.domain.model.Workout

import androidx.compose.ui.graphics.Color
import com.petrocini.progressodecarga.presentation.commom.utils.getDayColorFromDate
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DaySection(
    date: String,
    workouts: List<Workout>,
    selectedWorkouts: List<String>,
    onSelect: (Workout) -> Unit
) {
    val dayColor = remember (date) { getDayColorFromDate(date) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp, 32.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(dayColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Dia $date",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        workouts.forEach { workout ->
            WorkoutCard(
                workout = workout,
                isSelected = selectedWorkouts.contains(workout.id),
                onSelect = { onSelect(it) }
            )
        }
    }
}
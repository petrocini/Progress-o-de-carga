package com.petrocini.progressodecarga.presentation.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseCard(
    name: String,
    type: String,
    muscle: String,
    difficulty: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .shadow(2.dp, shape = RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("Tipo: $type", style = MaterialTheme.typography.bodySmall)
            Text("Músculo: $muscle", style = MaterialTheme.typography.bodySmall)
            Text("Dificuldade: $difficulty", style = MaterialTheme.typography.bodySmall)
        }
    }
}

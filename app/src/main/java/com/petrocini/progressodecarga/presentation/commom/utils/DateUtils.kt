package com.petrocini.progressodecarga.presentation.commom.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun getDayColorFromDate(dateStr: String): Color {
    return try {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.parse(dateStr)
        val calendar = Calendar.getInstance().apply { time = date!! }
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> Color(0xFF2196F3)
            Calendar.TUESDAY -> Color(0xFF4CAF50)
            Calendar.WEDNESDAY -> Color(0xFFFFC107)
            Calendar.THURSDAY -> Color(0xFFFF9800)
            Calendar.FRIDAY -> Color(0xFFF44336)
            Calendar.SATURDAY -> Color(0xFF9C27B0)
            Calendar.SUNDAY -> Color(0xFFE91E63)
            else -> Color.Gray
        }
    } catch (e: Exception) {
        Color.Gray
    }
}

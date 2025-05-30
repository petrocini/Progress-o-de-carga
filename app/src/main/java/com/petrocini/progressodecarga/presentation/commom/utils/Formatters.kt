package com.petrocini.progressodecarga.presentation.commom.utils

fun formatWeightInput(raw: String): String {
    val clean = raw.filter { it.isDigit() }
    if (clean.isEmpty()) return ""

    val number = clean.toLong()
    return if (clean.length <= 2) {
        "0," + clean.padStart(2, '0')
    } else {
        val left = clean.dropLast(2)
        val right = clean.takeLast(2)
        "$left,$right"
    }
}
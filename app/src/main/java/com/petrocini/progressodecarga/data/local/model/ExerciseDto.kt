package com.petrocini.progressodecarga.data.local.model

import com.petrocini.progressodecarga.domain.model.Exercise

data class ExerciseDto(
    val name: String,
    val type: String,
    val muscle: String,
    val difficulty: String,
    val instructions: String
) {
    fun toDomain() = Exercise(name, type, muscle, difficulty, instructions)
}

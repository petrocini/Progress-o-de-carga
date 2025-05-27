package com.petrocini.progressodecarga.data.remote.dto

data class ExerciseResponse(
    val results: List<ExerciseDto>
)

data class ExerciseDto(
    val id: Int,
    val name: String
)

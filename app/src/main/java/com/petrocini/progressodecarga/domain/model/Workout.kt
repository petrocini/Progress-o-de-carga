package com.petrocini.progressodecarga.domain.model

data class Workout(
    val id: String = "",
    val userId: String = "",
    val exercise: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val sets: List<WorkoutSet> = emptyList()
)

data class WorkoutSet(
    val repetitions: Int = 0,
    val weight: Float = 0f
)

package com.petrocini.progressodecarga.domain.repository

import com.petrocini.progressodecarga.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExercises(): List<Exercise>
}
package com.petrocini.progressodecarga.domain.repository

import com.petrocini.progressodecarga.domain.model.Workout
import kotlinx.coroutines.tasks.await

interface WorkoutRepository {
    suspend fun getWorkouts(): List<Workout>
    suspend fun getWorkoutById(id: String): Workout?
    suspend fun createWorkout(workout: Workout)
    suspend fun updateWorkout(workout: Workout)
    suspend fun deleteWorkouts(ids: List<String>)
}
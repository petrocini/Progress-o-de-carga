package com.petrocini.progressodecarga.presentation.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petrocini.progressodecarga.data.repository.ExerciseRepositoryImpl
import com.petrocini.progressodecarga.data.repository.WorkoutRepositoryImpl

class AddWorkoutViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val workoutRepo = WorkoutRepositoryImpl()
        val exerciseRepo = ExerciseRepositoryImpl(context)
        return AddWorkoutViewModel(workoutRepo, exerciseRepo) as T
    }
}

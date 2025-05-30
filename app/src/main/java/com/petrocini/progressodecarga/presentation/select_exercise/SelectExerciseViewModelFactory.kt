package com.petrocini.progressodecarga.presentation.select_exercise
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petrocini.progressodecarga.data.repository.ExerciseRepositoryImpl
import com.petrocini.progressodecarga.domain.repository.ExerciseRepository

class SelectExerciseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository: ExerciseRepository = ExerciseRepositoryImpl(context)
        return SelectExerciseViewModel(repository) as T
    }
}

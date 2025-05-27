package com.petrocini.progressodecarga.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.data.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = WorkoutRepository()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    fun loadWorkouts() {
        viewModelScope.launch {
            _workouts.value = repository.getWorkouts()
        }
    }
}

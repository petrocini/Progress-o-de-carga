package com.petrocini.progressodecarga.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrocini.progressodecarga.data.repository.WorkoutRepositoryImpl
import com.petrocini.progressodecarga.domain.model.Exercise
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.domain.model.WorkoutSet
import com.petrocini.progressodecarga.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddWorkoutViewModel(
    private val workoutRepository: WorkoutRepositoryImpl,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var workoutToEdit: Workout? = null

    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName

    private val _selectedExercise = MutableStateFlow<Exercise?>(null)
    val selectedExercise: StateFlow<Exercise?> = _selectedExercise

    private val _sets = MutableStateFlow<List<WorkoutSet>>(emptyList())
    val sets: StateFlow<List<WorkoutSet>> = _sets

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    fun init(workout: Workout?) {
        if (workout != null && workoutToEdit == null) {
            workoutToEdit = workout
            _exerciseName.value = workout.exercise
            _sets.value = workout.sets
        }
    }

    fun loadExercises() {
        viewModelScope.launch {
            _exercises.value = exerciseRepository.getExercises()

            if (_exerciseName.value.isNotBlank()) {
                val found = _exercises.value.find {
                    it.name.equals(_exerciseName.value, ignoreCase = true)
                }
                _selectedExercise.value = found
            }
        }
    }

    fun setExerciseName(name: String) {
        _exerciseName.value = name

        val found = _exercises.value.find {
            it.name.equals(name, ignoreCase = true)
        }
        _selectedExercise.value = found
    }

    fun addSet(set: WorkoutSet) {
        _sets.value = _sets.value + set
    }

    fun saveWorkout(userId: String, onSuccess: () -> Unit) {
        val workout = Workout(
            id = workoutToEdit?.id ?: "",
            userId = userId,
            exercise = _exerciseName.value,
            sets = _sets.value
        )

        viewModelScope.launch {
            if (workoutToEdit == null) {
                workoutRepository.createWorkout(workout)
            } else {
                workoutRepository.updateWorkout(workout)
            }
            onSuccess()
        }
    }
}
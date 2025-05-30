package com.petrocini.progressodecarga.presentation.select_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrocini.progressodecarga.domain.model.Exercise
import com.petrocini.progressodecarga.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SelectExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    val filteredExercises: StateFlow<List<Exercise>> = combine(_query, _exercises) { query, all ->
        if (query.isBlank()) all
        else all.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun loadExercises() {
        viewModelScope.launch {
            _exercises.value = repository.getExercises()
        }
    }

    fun setQuery(value: String) {
        _query.value = value
    }
}

package com.petrocini.progressodecarga.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrocini.progressodecarga.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun checkIfUserLoggedIn(onLoggedIn: () -> Unit) {
        if (authRepository.isUserLoggedIn()) {
            onLoggedIn()
        }
    }

    fun signInWithGoogle(idToken: String?, onSuccess: () -> Unit) {
        if (idToken == null) return

        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            val result = authRepository.signInWithGoogle(idToken)
            _uiState.value = _uiState.value.copy(loading = false)
            if (result) {
                onSuccess()
            }
        }
    }
}
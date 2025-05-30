package com.petrocini.progressodecarga.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petrocini.progressodecarga.data.repository.FirebaseAuthRepository

class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = FirebaseAuthRepository()
        return AuthViewModel(repository) as T
    }
}
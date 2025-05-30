package com.petrocini.progressodecarga.domain.repository

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun signInWithGoogle(idToken: String): Boolean
}

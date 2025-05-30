package com.petrocini.progressodecarga.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.petrocini.progressodecarga.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository() : AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override fun isUserLoggedIn() = auth.currentUser != null

    override suspend fun signInWithGoogle(idToken: String): Boolean {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            auth.signInWithCredential(credential).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

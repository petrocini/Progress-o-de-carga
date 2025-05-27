package com.petrocini.progressodecarga.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.petrocini.progressodecarga.domain.model.Workout
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getWorkouts(): List<Workout> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return db.collection("workouts")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Workout::class.java)?.copy(id = it.id) }
    }
}
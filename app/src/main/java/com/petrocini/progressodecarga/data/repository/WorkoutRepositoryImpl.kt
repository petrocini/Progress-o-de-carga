package com.petrocini.progressodecarga.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.petrocini.progressodecarga.domain.model.Workout
import com.petrocini.progressodecarga.domain.repository.WorkoutRepository
import kotlinx.coroutines.tasks.await

class WorkoutRepositoryImpl(): WorkoutRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override suspend fun getWorkouts(): List<Workout> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return db.collection("workouts")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Workout::class.java)?.copy(id = it.id) }
    }

    override suspend fun getWorkoutById(id: String): Workout? {
        return db.collection("workouts")
            .document(id)
            .get()
            .await()
            .toObject(Workout::class.java)?.copy(id = id)
    }

    override suspend fun createWorkout(workout: Workout) {
        val docRef = db.collection("workouts").document()
        val workoutWithId = workout.copy(id = docRef.id)
        docRef.set(workoutWithId).await()
    }

    override suspend fun updateWorkout(workout: Workout) {
        val docRef = db.collection("workouts").document()
        val workoutWithId = workout.copy(id = docRef.id)
        db.collection("workouts").document(workout.id).set(workout).await()
    }

    override suspend fun deleteWorkouts(ids: List<String>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        ids.forEach { id ->
            db.collection("workouts").document(id).delete().await()
        }
    }
}
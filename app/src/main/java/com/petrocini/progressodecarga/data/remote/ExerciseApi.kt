package com.petrocini.progressodecarga.data.remote

import com.petrocini.progressodecarga.data.remote.dto.ExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {
    @GET("exercise/")
    suspend fun getExercises(
        @Query("language") language: Int = 2, // 2 = English
        @Query("limit") limit: Int = 100
    ): ExerciseResponse
}
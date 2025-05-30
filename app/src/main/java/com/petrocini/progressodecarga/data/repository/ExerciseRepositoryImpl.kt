package com.petrocini.progressodecarga.data.repository

import android.content.Context
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.petrocini.progressodecarga.data.local.model.ExerciseDto
import com.petrocini.progressodecarga.domain.model.Exercise
import com.petrocini.progressodecarga.domain.repository.ExerciseRepository

class ExerciseRepositoryImpl(private val context: Context) : ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        val json = context.assets.open("exercises_ptbr.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<ExerciseDto>>() {}.type
        return Gson().fromJson<List<ExerciseDto>>(json, type).map { it.toDomain() }
    }
}

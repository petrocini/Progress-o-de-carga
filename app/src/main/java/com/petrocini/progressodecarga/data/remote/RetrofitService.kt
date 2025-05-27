package com.petrocini.progressodecarga.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {
    val api: ExerciseApi = Retrofit.Builder()
        .baseUrl("https://wger.de/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ExerciseApi::class.java)
}

package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Area
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceArea {
    @GET("area")
    fun getAreas(
        @Query("lang") language: String = "es",
        @Query("encr") encrypt: Int = 0
    ): Call<List<Area>>
}

package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Area
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceArea {

    @GET("area")
    fun getAreas(): Call<List<Area>>
}
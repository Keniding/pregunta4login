package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Reserva
import com.example.pregunta4_login.models.Rol
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceRol {

    @GET("rol")
    fun getRoles(): Call<List<Rol>>

    @GET("rol/{id}")
    fun getRol(@Path("id") id: Int): Call<Rol>
}
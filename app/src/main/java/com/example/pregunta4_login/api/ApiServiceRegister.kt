package com.example.pregunta4_login.api

import retrofit2.Response
import com.example.pregunta4_login.models.Usuario
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceRegister {

    @POST("auth/create")
    suspend fun createUser(@Body usuario: Usuario): Response<Usuario>
}

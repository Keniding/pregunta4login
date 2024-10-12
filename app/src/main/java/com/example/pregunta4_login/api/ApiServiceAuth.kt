package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Login
import com.example.pregunta4_login.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceAuth {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: Login): Response<LoginResponse>
}

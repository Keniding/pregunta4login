package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServiceAuth {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: Login): Response<LoginResponse>

    @GET("auth/me")
    suspend fun me(): Response<Usuario>

    @PUT("auth/update")
    suspend fun updatePassword(@Body updateRequest: UpdatePasswordRequest): Response<Mensaje>

    @POST("auth/recover")
    suspend fun recoverPassword(@Body recoverRequest: RecoverPasswordRequest): Response<Mensaje>

    @PUT("auth/status")
    suspend fun changeStatus(@Body statusRequest: StatusRequest): Response<Mensaje>
}

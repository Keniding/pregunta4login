package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Login
import com.example.pregunta4_login.models.LoginResponse
import com.example.pregunta4_login.models.Usuario
import com.example.pregunta4_login.response.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServiceMe {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: Login): Response<LoginResponse>

    @GET("auth/me")
    suspend fun me(): Response<Usuario>

    @Multipart
    @POST("v1/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Query("lang") lang: String? = null,
        @Query("encr") encr: String? = null
    ): Response<ImageUploadResponse>
}

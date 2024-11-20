package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.SocialLoginRequest
import com.example.pregunta4_login.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceSocial {
    @POST("social")
    suspend fun socialLogin(@Body socialLoginRequest: SocialLoginRequest): Response<LoginResponse>
}

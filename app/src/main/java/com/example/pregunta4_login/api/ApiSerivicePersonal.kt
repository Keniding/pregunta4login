package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Personal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiSerivicePersonal {

    @GET("personal")
    fun getPersonal(): Call<List<Personal>>

    @POST("personal")
    fun createPersonal(personal: Personal): Response<Personal>
}
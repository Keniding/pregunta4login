package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Personal
import com.example.pregunta4_login.models.Mensaje
import com.example.pregunta4_login.models.StatusRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServicePersonal {
    @GET("personal")
    fun getPersonal(): Call<List<Personal>>

    @POST("personal")
    fun createPersonal(@Body personal: Personal): Response<Personal>

    @GET("personal/found/{dni}")
    fun findPersonal(@Path("dni") dni: String): Call<Personal>

    @PUT("personal/update")
    fun updatePersonal(@Body personal: Personal): Response<Mensaje>

    @PUT("personal/status/{dni}")
    fun changeStatus(@Path("dni") dni: String, @Body statusRequest: StatusRequest): Response<Mensaje>
}

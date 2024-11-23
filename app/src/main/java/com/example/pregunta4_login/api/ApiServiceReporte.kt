// ApiServiceReporte.kt
package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Incidencia
import com.example.pregunta4_login.utils.CustomResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceReporte {
    @GET("reporte")
    fun getReporte(
        @Query("lang") language: String? = null
    ): Call<CustomResponse<List<Incidencia>>>
}

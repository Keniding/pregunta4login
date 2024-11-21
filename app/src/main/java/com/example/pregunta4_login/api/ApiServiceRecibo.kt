package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceRecibo {
    @GET("api/v1/recibos")
    fun getRecibos(@Query("page") page: Int): Call<ReciboPaginatedResponse>

    @POST("api/v1/recibos")
    fun crearRecibo(@Body recibo: ReciboRequest): Call<Recibo>

    @GET("api/v1/recibos/find")
    fun findRecibo(
        @Query("idre") idRecibo: Int,
        @Query("lang") language: String? = null,
        @Query("encr") encrypt: String? = null
    ): Call<ReciboDetallado>


    @GET("recibos/{dni}/dni")
    suspend fun getRecibosByDni(@Path("dni") dni: String): Response<List<ReciboDetallado>>
}

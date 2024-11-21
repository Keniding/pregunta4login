package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceReserva {
    @GET("reserva")
    fun getReservas(): Call<List<Reserva>>

    @GET("reserva/available")
    fun getReservasDisponibles(
        @Query("fecha_reserva") fechaReserva: String,
        @Query("id_area") idArea: Int
    ): Call<List<String>>

    @POST("reserva")
    fun crearReserva(@Body reserva: ReservaRequest): Call<Mensaje>
}

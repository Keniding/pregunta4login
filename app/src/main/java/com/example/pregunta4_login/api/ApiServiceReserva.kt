package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceReserva {
    @GET("reserva")
    fun getReservas(): Call<List<Reserva>>

    @POST("reserva/available")
    fun getReservasDisponibles(@Body reserva: HorarioReservaRequest): Call<List<String>>

    @POST("reserva")
    fun crearReserva(@Body reserva: ReservaRequest): Call<Mensaje>
}

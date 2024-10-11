package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.Reserva
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceReserva {

    @GET("reservas")
    fun getReservas(): Call<List<Reserva>>

    @GET("reservas/{id}")
    fun getReserva(@Path("id") id: Int): Call<Reserva>
}

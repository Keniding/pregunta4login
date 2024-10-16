package com.example.pregunta4_login.api

import com.example.pregunta4_login.models.HorarioReservaRequest
import com.example.pregunta4_login.models.Mensaje
import com.example.pregunta4_login.models.Reserva
import com.example.pregunta4_login.models.ReservaRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceReserva {

    /*
    @GET("reservas")
    fun getReservas(): Call<List<Reserva>>

    @GET("reservas/{id}")
    fun getReserva(@Path("id") id: Int): Call<Reserva>

     */

    @GET("reserva")
    fun getReservas(): Call<List<Reserva>>

    @POST("reserva/disponible")
    fun getReservasDisponibles(@Body reserva: HorarioReservaRequest): Call<List<String>>

    @POST("reserva")
    fun crearReserva(@Body reserva: ReservaRequest): Call<Mensaje>
}

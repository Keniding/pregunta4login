package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Reserva(
    @SerializedName("id_reserva") val idReserva: Int,
    @SerializedName("area") val area: String,
    @SerializedName("fecha_reserva") val fechaReserva: String,
    @SerializedName("hora_inicio") val horaInicio: String,
    @SerializedName("hora_fin") val horaFin: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String,
    @SerializedName("estado") val estado: String
)

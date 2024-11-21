package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class ReciboRequest(
    @SerializedName("description") val description: String,
    @SerializedName("fecha_reserva") val fechaReserva: String,
    @SerializedName("hora_fin") val horaFin: String,
    @SerializedName("hora_inicio") val horaInicio: String,
    @SerializedName("id_area") val idArea: Int
)
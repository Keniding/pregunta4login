package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Reserva(
    @SerializedName("id_reserva") val idReserva: Int,
    var estado: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String,
    @SerializedName("fecha_reserva") val fechaReserva: String,
    @SerializedName("hora_fin") val horaFin: String,
    @SerializedName("hora_inicio") val horaInicio: String,
    @SerializedName("id_area") val idArea: Int,
    @SerializedName("id_propietario") val idPropietario: Int
)

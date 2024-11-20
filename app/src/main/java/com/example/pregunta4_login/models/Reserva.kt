package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Reserva(
    @SerializedName("id") var id: Int,
    @SerializedName("id_area") var idArea: Int,
    @SerializedName("fecha_reserva") var fechaReserva: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String,
    @SerializedName("hora_inicio") var horaInicio: String,
    @SerializedName("hora_fin") var horaFin: String,
    @SerializedName("descripcion") var descripcion: String,
    @SerializedName("estado") var estado: Int
)

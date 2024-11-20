package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class ReservaRequest(
    @SerializedName("id_area") var idArea: Int,
    @SerializedName("fecha_reserva") var fechaReserva: String,
    @SerializedName("hora_inicio") var horaInicio: String,
    @SerializedName("hora_fin") var horaFin: String,
    @SerializedName("descripcion") var descripcion: String
)

package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class HorarioReservaRequest(
    @SerializedName("id_area") var area: Int,
    @SerializedName("fecha_reserva") var fechaReserva: String

) {
}
package com.example.pregunta4_login.models

data class ReservaRequest(
    val id_area: Int,
    val fecha_reserva: String,
    val hora_inicio: String,
    val hora_fin: String,
    val descripcion: String
)
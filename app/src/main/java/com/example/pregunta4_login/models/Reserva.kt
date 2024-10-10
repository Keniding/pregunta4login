package com.example.pregunta4_login.models

import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

data class Reserva(
    val idReserva: Int,
    val idPropietario: Int,
    val idArea: Int,
    val fechaReserva: Date,
    val horaInicio: Time,
    val horaFin: Time,
    val fechaCreacion: Timestamp,
    val estado: Boolean
)

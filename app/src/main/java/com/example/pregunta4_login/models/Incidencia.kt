package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Incidencia(
    @SerializedName("id_incidencia") val idIncidencia: Int,
    @SerializedName("id_area") val idArea: Int,
    @SerializedName("dni") val dni: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String,
    @SerializedName("detalleIncidencia") val detalleIncidencia: DetalleIncidencia?,
    @SerializedName("area") val area: Area?
)


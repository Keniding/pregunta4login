package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class DetalleIncidencia(
    @SerializedName("id_incidencia") val idIncidencia: Int,
    @SerializedName("descripcion") val descripcion: String
)
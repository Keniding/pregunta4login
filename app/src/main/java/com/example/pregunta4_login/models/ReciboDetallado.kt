package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class ReciboDetallado(
    @SerializedName("id_recibo") val idRecibo: Int,
    @SerializedName("servicio") val servicioNombre: String,
    @SerializedName("dni") val dni: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("url") val url: String,
    @SerializedName("estado") val estado: String
)


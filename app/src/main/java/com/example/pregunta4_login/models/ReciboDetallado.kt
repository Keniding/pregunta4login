package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class ReciboDetallado(
    @SerializedName("id_recibo") val idRecibo: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("dni") val dni: String,
    @SerializedName("id_servicio") val idServicio: String,
    @SerializedName("url") val url: String,
    @SerializedName("servicio") val servicio: Servicio?,
    @SerializedName("usuario") val usuario: Usuario?
)


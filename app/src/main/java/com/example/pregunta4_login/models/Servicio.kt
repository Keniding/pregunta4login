package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Servicio(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String
)

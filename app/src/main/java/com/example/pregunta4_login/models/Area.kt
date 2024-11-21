package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("id_area") val id: Int,
    @SerializedName("nombre_area") val nombre: String
)


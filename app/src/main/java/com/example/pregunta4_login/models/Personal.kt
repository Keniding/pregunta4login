package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Personal (
    @SerializedName("dni") val dni: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("foto") val foto: String,
    @SerializedName("id_rol") val id_rol: Int,
    @SerializedName("fecha_creacion") val fecha_creacion: String,
    @SerializedName("estado") val estado: Int
)
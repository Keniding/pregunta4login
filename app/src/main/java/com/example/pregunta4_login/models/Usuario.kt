package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("dni") val dni: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String, // Ocho caracteres mínimo
    @SerializedName("id_rol") val idRol: Int
)


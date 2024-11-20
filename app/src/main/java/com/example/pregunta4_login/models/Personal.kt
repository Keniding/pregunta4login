package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Personal(
    @SerializedName("dni") var dni: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("email") var email: String,
    @SerializedName("foto") var foto: String?,
    @SerializedName("id_rol") var idRol: Int,
    @SerializedName("estado") var estado: Int,
    @SerializedName("fecha_creacion") val fecha_creacion: String,
)

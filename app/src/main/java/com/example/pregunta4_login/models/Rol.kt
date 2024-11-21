package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

data class Rol(
    @SerializedName("id_rol") var id: Int,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String,
    val estado: Boolean
)
{
    override fun toString(): String {
        return nombre
    }
}

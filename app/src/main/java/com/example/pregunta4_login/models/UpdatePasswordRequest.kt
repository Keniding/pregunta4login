package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class UpdatePasswordRequest(
    @SerializedName("dni") var dni: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("id_rol") var idRol: Int
)

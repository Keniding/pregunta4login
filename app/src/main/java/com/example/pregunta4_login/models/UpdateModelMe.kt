package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class UpdateModelMe (
    @SerializedName("nombre") var nombre: String,
    @SerializedName("dni") var dni: String,
    @SerializedName("email") var email: String,
    @SerializedName("image") var image: String?,
    @SerializedName("id_rol") var idRol: Int
)

package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Area(
    @SerializedName("id") var id: Int,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("descripcion") var descripcion: String
)

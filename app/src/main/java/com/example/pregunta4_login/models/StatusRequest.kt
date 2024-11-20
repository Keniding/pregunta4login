package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class StatusRequest(
    @SerializedName("dni") var dni: String,
    @SerializedName("estado") var estado: Int
)

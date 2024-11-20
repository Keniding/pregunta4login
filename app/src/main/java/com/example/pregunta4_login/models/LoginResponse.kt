package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("token") var token: String,
    @SerializedName("user") var usuario: Usuario
)

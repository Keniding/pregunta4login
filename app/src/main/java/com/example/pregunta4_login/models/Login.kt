package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Login (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
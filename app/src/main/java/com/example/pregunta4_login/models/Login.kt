package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class Login(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)

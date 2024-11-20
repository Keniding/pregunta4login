package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class RecoverPasswordRequest(
    @SerializedName("dni") var dni: String,
    @SerializedName("new_password") var newPassword: String
)

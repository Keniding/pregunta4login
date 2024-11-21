package com.example.pregunta4_login.utils

import com.google.gson.annotations.SerializedName

data class CustomResponse<T>(
    @SerializedName("data") val data: T,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

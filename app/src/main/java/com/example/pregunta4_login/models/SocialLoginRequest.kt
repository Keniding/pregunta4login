package com.example.pregunta4_login.models

import com.google.gson.annotations.SerializedName

class SocialLoginRequest(
    @SerializedName("driver") var driver: String,
    @SerializedName("accessToken") var accessToken: String
)

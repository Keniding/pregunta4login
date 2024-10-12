package com.example.pregunta4_login.services

import com.example.pregunta4_login.api.ApiServiceAuth
import com.example.pregunta4_login.api.ApiServiceRegister
import com.example.pregunta4_login.api.ApiServiceReserva
import com.example.pregunta4_login.api.ApiServiceRol
import com.example.pregunta4_login.config.RetrofitConfig

object ApiServiceFactory {

    private const val BASE_URL_FLASK = "http://192.168.8.105:9000/"
    private const val BASE_URL_LARAVEL = "http://192.168.8.105:9001/api/v1/"

    private val laravelRetrofit = RetrofitConfig(BASE_URL_LARAVEL)
    private val flaskRetrofit = RetrofitConfig(BASE_URL_FLASK)

    // Flask API service
    val flaskInstance: ApiServiceReserva by lazy {
        flaskRetrofit.createService(ApiServiceReserva::class.java)
    }

    // Laravel API service
    val laravelInstance: ApiServiceRol by lazy {
        laravelRetrofit.createService(ApiServiceRol::class.java)
    }

    val registerInstance: ApiServiceRegister by lazy {
        laravelRetrofit.createService(ApiServiceRegister::class.java)
    }

    val loginInstance: ApiServiceAuth by lazy {
        laravelRetrofit.createService(ApiServiceAuth::class.java)
    }

}

package com.example.pregunta4_login.config

import com.example.pregunta4_login.api.ApiServiceReserva
import com.example.pregunta4_login.api.ApiServiceRol
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL_FLASK = "http://192.168.8.105:9000/"
    private const val BASE_URL_LARAVEL = "http://192.168.8.105:9001/api/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val flaskInstance: ApiServiceReserva by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_FLASK)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceReserva::class.java)
    }

    val laravelInstance: ApiServiceRol by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_LARAVEL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceRol::class.java)
    }
}

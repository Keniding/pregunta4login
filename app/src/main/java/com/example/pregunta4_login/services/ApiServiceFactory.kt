package com.example.pregunta4_login.services

import android.content.Context
import com.example.pregunta4_login.api.ApiSerivicePersonal
import com.example.pregunta4_login.api.ApiServiceArea
import com.example.pregunta4_login.api.ApiServiceAuth
import com.example.pregunta4_login.api.ApiServiceRegister
import com.example.pregunta4_login.api.ApiServiceReserva
import com.example.pregunta4_login.api.ApiServiceRol
import com.example.pregunta4_login.config.RetrofitConfig
import com.example.pregunta4_login.utils.getTokenSecurely

object ApiServiceFactory {

    private const val BASE_URL_FLASK = "http://192.168.8.105:9000/"
    private const val BASE_URL_LARAVEL = "http://192.168.8.105:9002/api/v1/"

    fun createLaravelRetrofit(context: Context): RetrofitConfig {
        return RetrofitConfig(BASE_URL_LARAVEL, provideTokenProvider(context))
    }

    private fun provideTokenProvider(context: Context): () -> String = {
        getTokenSecurely(context) ?: ""
    }

    private val flaskRetrofit = RetrofitConfig(BASE_URL_FLASK)

    val flaskInstance: ApiServiceReserva by lazy {
        flaskRetrofit.createService(ApiServiceReserva::class.java, authenticated = false)
    }

    val laravelInstance: ApiServiceRol by lazy {
        RetrofitConfig(BASE_URL_LARAVEL).createService(ApiServiceRol::class.java, authenticated = false)
    }

    val registerInstance: ApiServiceRegister by lazy {
        RetrofitConfig(BASE_URL_LARAVEL).createService(ApiServiceRegister::class.java, authenticated = false)
    }

    val loginInstance: ApiServiceAuth by lazy {
        RetrofitConfig(BASE_URL_LARAVEL).createService(
            ApiServiceAuth::class.java,
            authenticated = false
        )
    }

    var meInstance: ApiServiceAuth? = null
        private set

    fun initializeMeInstance(context: Context) {
        meInstance = createLaravelRetrofit(context).createService(ApiServiceAuth::class.java, authenticated = true)
    }

    var reservaInstance: ApiServiceReserva? = null

    fun initializeReservaInstance(context: Context) {
        reservaInstance = createLaravelRetrofit(context).createService(ApiServiceReserva::class.java, authenticated = true)
    }

    fun cargarPersonalInstance(context: Context): ApiSerivicePersonal {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiSerivicePersonal::class.java, authenticated = true)
    }

    fun cargarHorariosInstance(context: Context): ApiServiceReserva {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceReserva::class.java, authenticated = true)
    }

    fun cargarAreasInstance(context: Context): ApiServiceArea {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceArea::class.java, authenticated = true)
    }

    fun crearReservaInstance(context: Context): ApiServiceReserva {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceReserva::class.java, authenticated = true)
    }
}

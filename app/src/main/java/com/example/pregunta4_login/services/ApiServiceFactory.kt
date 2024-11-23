package com.example.pregunta4_login.services

import android.content.Context
import com.example.pregunta4_login.api.*
import com.example.pregunta4_login.api.ApiServiceArea
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

    val loginInstance: ApiServiceMe by lazy {
        RetrofitConfig(BASE_URL_LARAVEL).createService(
            ApiServiceMe::class.java,
            authenticated = false
        )
    }

    var meInstance: ApiServiceMe? = null
        private set

    fun initializeMeInstance(context: Context) {
        meInstance = createLaravelRetrofit(context).createService(ApiServiceMe::class.java, authenticated = true)
    }

    var updatePhoto: ApiServiceMe? = null

    fun initializeUpdatePhoto(context: Context) {
        updatePhoto = createLaravelRetrofit(context).createService(ApiServiceMe::class.java, authenticated = true)
    }

    var reservaInstance: ApiServiceReserva? = null

    fun initializeReservaInstance(context: Context) {
        reservaInstance = createLaravelRetrofit(context).createService(ApiServiceReserva::class.java, authenticated = true)
    }

    fun cargarPersonalInstance(context: Context): ApiServicePersonal {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServicePersonal::class.java, authenticated = true)
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

    fun cargarSocialInstance(context: Context): ApiServiceSocial {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceSocial::class.java, authenticated = false)
    }

    fun cargarUploadInstance(context: Context): ApiServiceUpload {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceUpload::class.java, authenticated = false)
    }

    fun cargarRolInstance(context: Context): ApiServiceRol {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceRol::class.java, authenticated = false)
    }

    fun cargarAuthInstance(context: Context): ApiServiceMe {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceMe::class.java, authenticated = true)
    }

    fun cargarPersonalAuthInstance(context: Context): ApiServicePersonal {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServicePersonal::class.java, authenticated = true)
    }

    fun cargarReciboInstance(context: Context): ApiServiceRecibo {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceRecibo::class.java, authenticated = true)
    }

    fun cargarReporteInstance(context: Context): ApiServiceReporte {
        val laravelRetrofit = createLaravelRetrofit(context)
        return laravelRetrofit.createService(ApiServiceReporte::class.java, authenticated = true)
    }
}

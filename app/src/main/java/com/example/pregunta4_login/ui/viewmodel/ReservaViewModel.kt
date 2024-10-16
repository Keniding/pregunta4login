package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import android.util.Log
import com.example.pregunta4_login.models.Reserva
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModel

class ReservaViewModel(application: Application) : BaseViewModel<List<Reserva>>(ApiServiceFactory) {

    init {
        Log.d("ReservaViewModel", "Inicializando ReservaViewModel")
        ApiServiceFactory.initializeReservaInstance(application)
    }

    fun fetchReservas(onResult: (String, List<Reserva>?) -> Unit) {
        Log.d("ReservaViewModel", "fetchReservas llamado")
        val reservaService = ApiServiceFactory.reservaInstance
        if (reservaService != null) {
            Log.d("ReservaViewModel", "reservaService no es null, realizando llamada a la API")
            reservaService.getReservas().enqueue(object : retrofit2.Callback<List<Reserva>> {
                override fun onResponse(call: retrofit2.Call<List<Reserva>>, response: retrofit2.Response<List<Reserva>>) {
                    if (response.isSuccessful) {
                        val reservas = response.body()
                        if (reservas != null) {
                            Log.d("ReservaViewModel", "Reservas obtenidas: ${reservas.size} reservas")
                            onResult("Reservas obtenidas con éxito", reservas)
                        } else {
                            Log.d("ReservaViewModel", "Error: No se pudieron obtener las reservas")
                            onResult("Error al obtener las reservas", null)
                        }
                    } else {
                        Log.e("ReservaViewModel", "Error en la API: ${response.message()}")
                        onResult("Error en la API: ${response.message()}", null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<Reserva>>, t: Throwable) {
                    Log.e("ReservaViewModel", "Fallo en la conexión: ${t.message}")
                    onResult("Fallo en la conexión: ${t.message}", null)
                }
            })
        } else {
            Log.e("ReservaViewModel", "Error de configuración: reservaService es null")
            onResult("Error de configuración", null)
        }
    }
}

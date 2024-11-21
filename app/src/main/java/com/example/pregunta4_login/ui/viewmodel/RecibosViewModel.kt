package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pregunta4_login.models.ReciboDetallado
import com.example.pregunta4_login.services.ApiServiceFactory
import kotlinx.coroutines.launch

class RecibosViewModel(application: Application) : AndroidViewModel(application) {

    private val _recibos = MutableLiveData<List<ReciboDetallado>>()
    val recibos: LiveData<List<ReciboDetallado>> = _recibos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarRecibos() {
        val dni = getApplication<Application>()
            .getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            .getString("dni", "") ?: ""

        if (dni.isNotEmpty()) {
            cargarRecibosByDni(dni)
        }
    }

    fun cargarRecibosByDni(dni: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val reciboService = ApiServiceFactory.cargarReciboInstance(getApplication())
                val response = reciboService.getRecibosByDni(dni)

                if (response.isSuccessful) {
                    _recibos.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al cargar los recibos: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
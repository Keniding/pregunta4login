package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pregunta4_login.models.Incidencia
import com.example.pregunta4_login.services.ApiServiceFactory
import kotlinx.coroutines.launch
import retrofit2.await

class ReporteViewModel(application: Application) : AndroidViewModel(application) {

    private val _reportes = MutableLiveData<List<Incidencia>>()
    val reportes: LiveData<List<Incidencia>> = _reportes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarReportes() {
        val dni = getApplication<Application>()
            .getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            .getString("dni", "") ?: ""

        if (dni.isNotEmpty()) {
            cargarReportesByDni(dni)
        }
    }

    private fun cargarReportesByDni(dni: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val incidenciaService = ApiServiceFactory.cargarReporteInstance(getApplication())
                val response = incidenciaService.getReporte().await()

                _reportes.value = response.data ?: emptyList()

            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

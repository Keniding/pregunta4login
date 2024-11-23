package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pregunta4_login.models.Usuario
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModel

class MeViewModel(application: Application) : BaseViewModel<Usuario>(ApiServiceFactory) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    init {
        ApiServiceFactory.initializeMeInstance(application)
    }

    fun fetchUserProfile(onResult: (String, Usuario?) -> Unit) {
        Log.d("MeViewModel", "fetchUserProfile llamado")

        val meService = ApiServiceFactory.meInstance
        if (meService != null) {
            performApiCall(
                apiCall = { meService.me() },
                onResult = { usuario ->
                    if (usuario != null) {
                        Log.d("MeViewModel", "Usuario recibido: $usuario")
                        saveUserToPreferences(usuario)
                        onResult("Perfil obtenido con éxito", usuario)
                        Log.d("MeViewModel", "Perfil obtenido con éxito: $usuario")
                    } else {
                        Log.d("MeViewModel", "Usuario es nulo")
                        onResult("Error al obtener el perfil", null)
                    }
                },
                onError = { errorMessage ->
                    Log.d("MeViewModel", "Error en la API: $errorMessage")
                    onResult(errorMessage, null)
                }
            )
        } else {
            Log.e("MeViewModel", "meInstance no está inicializado")
            onResult("Error de configuración", null)
        }
    }

    private fun saveUserToPreferences(usuario: Usuario) {
        with(sharedPreferences.edit()) {
            putString("dni", usuario.dni)
            putInt("id_rol", usuario.idRol)
            putString("nombre", usuario.nombre)
            putString("email", usuario.email)
            putString("password", usuario.password)
            putString("profile_image_url", usuario.image)
            Log.d("MeViewModel", "Usuario guardado en preferencias: ${usuario.nombre}")
            apply()
        }
    }
}

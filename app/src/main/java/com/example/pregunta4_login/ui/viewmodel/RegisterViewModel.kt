package com.example.pregunta4_login.ui.viewmodel

import com.example.pregunta4_login.models.Usuario
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModel

class RegisterViewModel(apiService: ApiServiceFactory) : BaseViewModel<Usuario>(apiService) {

    fun guardarUsuario(user: Usuario, onResult: (String) -> Unit) {
        performApiCall(
            apiCall = { apiService.registerInstance.createUser(user) },
            onResult = {
                val successMessage = "Usuario registrado con éxito"
                onResult(successMessage)
            },
            onError = { errorMessage ->
                onResult(errorMessage)
            }
        )
    }
}

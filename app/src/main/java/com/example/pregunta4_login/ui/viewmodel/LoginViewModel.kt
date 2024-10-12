package com.example.pregunta4_login.ui.viewmodel

import com.example.pregunta4_login.models.Login
import com.example.pregunta4_login.models.LoginResponse
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModel

class LoginViewModel(apiService: ApiServiceFactory) : BaseViewModel<LoginResponse>(apiService) {

    fun login(user: Login, onResult: (String, String?) -> Unit) {
        performApiCall(
            apiCall = { apiService.loginInstance.login(user) },
            onResult = { response ->
                val token = response.token
                if (token.isNotEmpty()) {
                    onResult("Operación completada con éxito", token)
                } else {
                    onResult("Error al iniciar sesión", null)
                }
            },
            onError = { errorMessage ->
                onResult(errorMessage, null)
            }
        )
    }
}

package com.example.pregunta4_login.utils

import retrofit2.Response

fun <T> handleApiResponse(response: Response<T>, onSuccess: (T) -> Unit, onError: (String) -> Unit) {
    if (response.isSuccessful) {
        response.body()?.let { responseBody ->
            onSuccess(responseBody)
        } ?: onError("Respuesta vacía")
    } else {
        val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
        onError(errorMessage)
    }
}

package com.example.pregunta4_login.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.utils.handleApiResponse
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel<T>(val apiService: ApiServiceFactory) : ViewModel() {

    fun performApiCall(apiCall: suspend () -> Response<T>, onResult: (T) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiCall()
                handleApiResponse(response,
                    onSuccess = { result: T ->
                        onResult(result)
                    },
                    onError = { errorMessage ->
                        onError(errorMessage)
                    }
                )
            } catch (e: Exception) {
                onError("Error de red: ${e.message}")
            }
        }
    }
}

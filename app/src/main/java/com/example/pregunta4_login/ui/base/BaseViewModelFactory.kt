package com.example.pregunta4_login.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pregunta4_login.services.ApiServiceFactory

abstract class BaseViewModelFactory<T : ViewModel>(
    open val apiServiceFactory: ApiServiceFactory,
    private val viewModelClass: Class<T>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            return createViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    protected abstract fun createViewModel(): T
}

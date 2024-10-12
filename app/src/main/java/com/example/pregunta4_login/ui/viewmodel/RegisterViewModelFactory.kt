package com.example.pregunta4_login.ui.viewmodel

import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModelFactory

class RegisterViewModelFactory(
    apiServiceFactory: ApiServiceFactory
) : BaseViewModelFactory<RegisterViewModel>(apiServiceFactory, RegisterViewModel::class.java) {
    override fun createViewModel(): RegisterViewModel {
        return RegisterViewModel(apiServiceFactory)
    }
}

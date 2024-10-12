package com.example.pregunta4_login.ui.viewmodel

import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModelFactory

class LoginViewModelFactory(
    apiServiceFactory: ApiServiceFactory
) : BaseViewModelFactory<LoginViewModel>(apiServiceFactory, LoginViewModel::class.java) {
    override fun createViewModel(): LoginViewModel {
        return LoginViewModel(apiServiceFactory)
    }
}

package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModelFactory

class ReservaViewModelFactory(
    private val application: Application,
    override val apiServiceFactory: ApiServiceFactory
) : BaseViewModelFactory<ReservaViewModel>(apiServiceFactory, ReservaViewModel::class.java) {
    override fun createViewModel(): ReservaViewModel {
        return ReservaViewModel(application)
    }
}

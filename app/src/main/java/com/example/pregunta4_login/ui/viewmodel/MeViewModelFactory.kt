package com.example.pregunta4_login.ui.viewmodel

import android.app.Application
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.base.BaseViewModelFactory

class MeViewModelFactory(
    private val application: Application,
    override val apiServiceFactory: ApiServiceFactory
) : BaseViewModelFactory<MeViewModel>(apiServiceFactory, MeViewModel::class.java) {
    override fun createViewModel(): MeViewModel {
        return MeViewModel(application)
    }
}

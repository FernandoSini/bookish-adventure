package br.com.fernandosini.bookishadventure.screens.ViewModel.states

import com.russhwolf.settings.Settings

data class SplashState(
    var isLoading: Boolean = false,
    var isFirstTime: Boolean = true,
    val settings :Settings = Settings()

)
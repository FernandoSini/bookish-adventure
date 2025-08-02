package br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel.state

import br.com.flemis.bookishadventure.utils.Preferences

data class SplashState(
    var isLoading: Boolean = false,
    var isFirstTime: Boolean = true,
    val settings : Preferences = Preferences()
)
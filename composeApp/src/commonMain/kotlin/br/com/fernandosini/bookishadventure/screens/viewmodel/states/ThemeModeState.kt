package br.com.fernandosini.bookishadventure.screens.viewmodel.states

import com.russhwolf.settings.Settings


data class ThemeModeState(
    var isDarkMode: Boolean = false,
    val settings: Settings = Settings()
)
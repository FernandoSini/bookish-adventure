package br.com.flemis.bookishadventure.features.settings.domain.repository

import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.state.ThemeModeState
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ThemeRepository {
    suspend fun changeTheme(
        state: MutableStateFlow<ThemeModeState>,
        preferences: Preferences
    ): StateFlow<ThemeModeState>
}
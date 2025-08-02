package br.com.flemis.bookishadventure.features.settings.data.repository.implementations

import br.com.flemis.bookishadventure.features.settings.domain.repository.ThemeRepository
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.state.ThemeModeState
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ThemeRepositoryImpl : ThemeRepository {

    override suspend fun changeTheme(
        state: MutableStateFlow<ThemeModeState>,
        preferences: Preferences
    ): StateFlow<ThemeModeState> {
        state.update { currentState ->
            currentState.copy(
                isDarkMode = !currentState.isDarkMode
            )
        }
        preferences.putBoolean("darkMode", state.value.isDarkMode)
        return state
    }
}
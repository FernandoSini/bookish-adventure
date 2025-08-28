package br.com.flemis.bookishadventure.features.settings.domain.usecases

import br.com.flemis.bookishadventure.features.settings.domain.repository.ThemeRepository
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.state.ThemeModeState
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ThemeUseCase : KoinComponent {
    private val themeRepository: ThemeRepository by inject()
    suspend fun changeTheme(
        state: MutableStateFlow<ThemeModeState>,
        preferences: Preferences
    ): StateFlow<ThemeModeState> {
        return themeRepository.changeTheme(state, preferences)
    }
}
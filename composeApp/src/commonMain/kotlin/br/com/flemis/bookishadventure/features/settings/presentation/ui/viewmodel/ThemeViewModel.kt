package br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.features.settings.domain.usecases.ThemeUseCase
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.state.ThemeModeState
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ThemeViewModel(
    private val preferences: Preferences
) : ViewModel(), KoinComponent {
    private val themeUseCase: ThemeUseCase by inject()

    private val _state = MutableStateFlow(ThemeModeState())


    var state = _state.onStart {
        // _state.value.isDarkMode = _state.value.settings.getBoolean("darkMode", false)
        _state.update { it.copy(isDarkMode = preferences.getBoolean("darkMode", false)) }
    }.stateIn(
        scope = viewModelScope, initialValue = ThemeModeState(), started = SharingStarted.Companion.Lazily
    )

    fun changeTheme(snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            state = themeUseCase.changeTheme(_state, preferences)
            //println("ThemeViewModel: ${state.value.isDarkMode}")
            when(getPlatform().name) {
                "android" -> snackbarHostState.showSnackbar(
                    message = "You need to restart the app to get all changes from theme mode"
                )

                else -> snackbarHostState.showSnackbar(
                    message = "Theme updated. Restart the app to see all changes."
                )
            }


        }
    }
}
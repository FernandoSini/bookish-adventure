package br.com.fernandosini.bookishadventure.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fernandosini.bookishadventure.screens.viewmodel.states.ThemeModeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ThemeViewModel : ViewModel() {

    private val _state = MutableStateFlow(ThemeModeState())


    val state = _state.onStart {
        // _state.value.isDarkMode = _state.value.settings.getBoolean("darkMode", false)
        _state.update { it.copy(isDarkMode = _state.value.settings.getBoolean("darkMode", false)) }
    }.stateIn(
        scope = viewModelScope, initialValue = ThemeModeState(), started = SharingStarted.Lazily
    )

    fun changeTheme() {
        //  _state.value.settings.putBoolean("darkMode", value)
       // _state.value.isDarkMode = !_state.value.isDarkMode
         _state.update { currentState ->
             currentState.copy(
                 isDarkMode = !currentState.isDarkMode
             )

         }

        _state.value.settings.putBoolean("darkMode", _state.value.isDarkMode)
    }
}
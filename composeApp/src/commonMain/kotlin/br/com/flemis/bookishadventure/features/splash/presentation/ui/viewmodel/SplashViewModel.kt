package br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel.state.SplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow(SplashState())

    /*  var loading: MutableStateFlow<Boolean>;
      var isLoading: StateFlow<Boolean>;
      private val settings: Settings;
      private var firstTimeInApp: MutableStateFlow<Boolean>;
       var isFirstTime: StateFlow<Boolean>;


      init {
          loading = MutableStateFlow<Boolean>(false)
          isLoading = loading.asStateFlow()
          settings = Settings()
          firstTimeInApp = MutableStateFlow<Boolean>(settings.getBoolean("firstTimeInApp", true))
          isFirstTime = firstTimeInApp.asStateFlow()
      }
  */
    val state = _state.onStart {

        /* _state.value.isLoading = true
         _state.value.isFirstTime = _state.value.settings.getBoolean("firstTimeInApp", true)*/
        _state.update {
            it.copy(
                isLoading = true,
                isFirstTime = _state.value.settings.getBoolean("firstTimeInApp", defaultValue = true)
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5_000L),
        initialValue = SplashState()
    )


    /*fun setFirstTimeInApp(value: Boolean) {

        viewModelScope.launch {
            settings.putBoolean("firstTimeInApp", value)
            firstTimeInApp.value = value
        }
    }*/
    fun setFirstTimeInApp(value: Boolean) {
        _state.value.settings.putBoolean("firstTimeInApp", value)
        _state.value.isFirstTime = value

    }


    fun loadUserInfo() {

        _state.value.isLoading = true

    }


}
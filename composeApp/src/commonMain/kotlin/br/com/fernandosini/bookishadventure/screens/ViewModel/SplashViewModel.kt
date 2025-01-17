package br.com.fernandosini.bookishadventure.screens.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.repository.db.getRoomDatabase
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    var loading : MutableStateFlow<Boolean>;
    var isLoading: StateFlow<Boolean>;
    private val settings :Settings;


    init {
        loading = MutableStateFlow<Boolean>(false)
        isLoading = loading.asStateFlow()
        settings = Settings()
    }

    fun loadUserInfo(){
        viewModelScope.launch {
            loading.value = true

        }

    }

}
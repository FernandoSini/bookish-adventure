package br.com.flemis.bookishadventure.utils

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

actual class SharedViewModel:ViewModel() {
    internal var darkTheme: MutableState<Boolean> = mutableStateOf(false)

    actual fun setDarkTheme(isDark: Boolean) {
        this.darkTheme.value = isDark
    }

    actual fun getDarkTheme(): Boolean {
        return this.darkTheme.value
    }

}
actual fun createSharedViewModelInstance(): SharedViewModel {
    return SharedViewModel()
}

internal fun saveSharedViewModel(
    sharedViewModel: SharedViewModel, outState: Bundle
) {
    outState.putBoolean("darkTheme", sharedViewModel.darkTheme.value)
}

internal fun restoreSharedViewModel(
    sharedViewModel: SharedViewModel, savedInstanceState: Bundle?
) {
    sharedViewModel.darkTheme.value = savedInstanceState?.getBoolean("darkTheme", false) ?: false
}

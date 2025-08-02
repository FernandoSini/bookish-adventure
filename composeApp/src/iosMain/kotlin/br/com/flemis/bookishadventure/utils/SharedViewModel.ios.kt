package br.com.flemis.bookishadventure.utils

import platform.Foundation.NSUserDefaults

actual class SharedViewModel {
    internal var isDarkTheme: Boolean = false

    actual fun setDarkTheme(isDark: Boolean): Unit {
        this.isDarkTheme = isDark
    }

    actual fun getDarkTheme(): Boolean {
        return this.isDarkTheme
    }
}
actual fun createSharedViewModelInstance(): SharedViewModel{
    return SharedViewModel()
}

internal fun saveSharedViewModel(sharedViewModel: SharedViewModel) {
    NSUserDefaults.standardUserDefaults.setObject(
        sharedViewModel.isDarkTheme,
        forKey = "user_input"
    )

}

internal fun restoreSharedViewModel(sharedViewModel: SharedViewModel): String {
    return NSUserDefaults.standardUserDefaults.stringForKey("user_input") ?: ""

}


package br.com.flemis.bookishadventure.utils

expect class SharedViewModel(){
    fun setDarkTheme(isDark: Boolean)
    fun getDarkTheme(): Boolean
}

expect fun createSharedViewModelInstance(): SharedViewModel


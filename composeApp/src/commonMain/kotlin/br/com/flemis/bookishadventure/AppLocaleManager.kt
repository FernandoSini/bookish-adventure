package br.com.flemis.bookishadventure

import androidx.compose.runtime.Composable

interface AppLocaleManager {
    fun getCurrentLocale(): String
    fun setCurrentLocale(code: String)
}

@Composable
expect fun rememberAppLocale():String
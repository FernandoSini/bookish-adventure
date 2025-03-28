package br.com.fernandosini.bookishadventure
import platform.AVFoundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.localeIdentifier
import platform.Foundation.systemLocale

class LanguageManager : AppLocaleManager {
    override fun setCurrentLocale(code: String) {
        NSUserDefaults.standardUserDefaults.setObject(listOf(code), forKey = "AppleLanguages")
        NSUserDefaults.standardUserDefaults.synchronize()
    }

    override fun getCurrentLocale(): String {
        return NSLocale.currentLocale.languageCode;
    }
}

@Composable
actual fun rememberAppLocale(): String {

    var nsLocale = LanguageManager().getCurrentLocale()
    return remember(nsLocale) {
        nsLocale
    }
}


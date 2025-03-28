package br.com.fernandosini.bookishadventure

import android.app.LocaleManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat

class LanguageManager(private val context: Context) : AppLocaleManager {


    override fun setCurrentLocale(code: String): Unit {

        //versao do android for >=13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(code);
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
        }
    }

    override fun getCurrentLocale(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            /*return context. getSystemService(LocaleManager::class.java).getApplicationLocales(context.packageName).get(0)
                .toLanguageTag() ?: "en-US"*/
            return context.resources.configuration.locales.get(0).toLanguageTag() ?:"en-US"
        } else {
            val Locales = AppCompatDelegate.getApplicationLocales()
          //  return AppCompatDelegate.getApplicationLocales().get(0)?.toLanguageTag() ?: "en-US"
            return context.resources.configuration.locales.get(0).toLanguageTag() ?: "en-US"
        }
    }

}

@Composable
actual fun rememberAppLocale(): String {
    val context = LocalContext.current
    val locale = LanguageManager(context).getCurrentLocale()

    return remember(locale) {
        locale
    }
}
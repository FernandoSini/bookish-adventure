package br.com.fernandosini.bookishadventure

import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.compose.ui.text.intl.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val appVersion: String = "${BuildConfig.VERSION_NAME}"

    fun getCurrentLocale(): String {
        return Locale.current.language
    }
    fun ChangeLocare(locale:String){

    }
}

actual fun getPlatformLocale(): String = AndroidPlatform().getCurrentLocale()
actual fun getPlatform(): Platform = AndroidPlatform()


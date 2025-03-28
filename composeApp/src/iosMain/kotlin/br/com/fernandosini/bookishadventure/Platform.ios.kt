package br.com.fernandosini.bookishadventure

import platform.Foundation.NSBundle
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val appVersion: String =
        NSBundle.mainBundle().infoDictionary()?.get("CFBundleShortVersionString").toString() ?: "-"

    fun getCurrentLocale(): String {
        return NSLocale.currentLocale.languageCode;
    }
}

actual fun getPlatformLocale():String = IOSPlatform().getCurrentLocale()
actual fun getPlatform(): Platform = IOSPlatform()
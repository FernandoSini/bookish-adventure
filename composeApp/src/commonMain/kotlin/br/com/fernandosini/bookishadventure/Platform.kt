package br.com.fernandosini.bookishadventure

interface Platform {
    val name: String
    val appVersion: String
}

expect fun getPlatform(): Platform
expect fun getPlatformLocale(): String

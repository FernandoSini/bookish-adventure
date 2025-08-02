package br.com.flemis.bookishadventure.utils

/**
 * Interface para armazenamento de chave-valor multiplataforma (expect).
 * Atua como uma abstração para SharedPreferences no Android e UserDefaults no iOS.
 */
expect class Preferences {
    constructor()
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String

    fun putInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int): Int

    fun putLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long): Long
    fun putMap(key: String, value: Map<String, String>)
    fun getMap(key: String): Map<String, String>

    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun clearPreferences(key: String?)

    // Outros tipos de dados podem ser adicionados conforme necessário (Long, Float, etc.)
}


expect fun getInstancePreferences(): Preferences

 object PreferenceInstance {
    val instance: Preferences by lazy { getInstancePreferences() }
}
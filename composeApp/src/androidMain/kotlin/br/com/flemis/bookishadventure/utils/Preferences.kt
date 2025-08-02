package br.com.flemis.bookishadventure.utils

import android.content.Context
import android.content.SharedPreferences
import br.com.flemis.bookishadventure.MyApplication

actual class Preferences(private val context: Context) {
    actual constructor() : this(MyApplication.applicationContext())

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    }

    actual fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
        println("Android: Saved String '$key' = '$value'")
    }

    actual fun getString(key: String, defaultValue: String): String {
        val value = preferences.getString(key, defaultValue) ?: defaultValue
        println("Android: Retrieved String '$key' = '$value'")
        return value
    }

    actual fun putMap(key: String, value: Map<String, String>) {
        val editor = preferences.edit()
        for ((k, v) in value) {
            editor.putString("$key.$k", v)
        }
        editor.apply()
        println("Android: Saved Map '$key' = '$value'")
    }

    actual fun getMap(key: String): Map<String, String> {
        val allEntries = preferences.all
        val map = mutableMapOf<String, String>()
        for ((k, v) in allEntries) {
            if (k.startsWith("$key.")) {
                map[k.removePrefix("$key.")] = v as String
            }
        }
        println("Android: Retrieved Map '$key' = '$map'")
        return map
    }

    actual fun putLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
        println("Android: Saved Long '$key' = '$value'")
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        val value = preferences.getLong(key, defaultValue)
        println("Android: Retrieved Long '$key' = '$value'")
        return value
    }

    actual fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
        println("Android: Saved Int '$key' = '$value'")
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        val value = preferences.getInt(key, defaultValue)
        println("Android: Retrieved Int '$key' = '$value'")
        return value
    }

    actual fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
        println("Android: Saved Boolean '$key' = '$value'")
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val value = preferences.getBoolean(key, defaultValue)
        println("Android: Retrieved Boolean '$key' = '$value'")
        return value
    }

    actual fun clearPreferences(key: String?) {
        if (!key.isNullOrEmpty()) {
            preferences.edit().remove(key).apply()
            println("Android: Cleared preference for key '$key'")
        } else {
            preferences.edit().clear()
            println("Android: Cleared all preferences")
        }
    }
}

actual fun getInstancePreferences(): Preferences {
    val myApplicationContext = MyApplication.applicationContext()
    return Preferences(myApplicationContext)
}
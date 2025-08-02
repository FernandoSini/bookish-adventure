package br.com.flemis.bookishadventure.utils

import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUserDefaults
import platform.Foundation.enumerateKeysAndObjectsUsingBlock
import platform.Foundation.enumerateKeysAndObjectsWithOptions
import platform.darwin.NSObject

/**
 * Implementação iOS de [SettingsStorage] usando NSUserDefaults.
 */
actual class Preferences {
    actual constructor()

    private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()

    actual fun putString(key: String, value: String) {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize() // Opcional, mas útil para garantir a gravação imediata
        println("iOS: Saved String '$key' = '$value'")
    }

    actual fun getString(key: String, defaultValue: String): String {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        val value = userDefaults.stringForKey(key)
        println("iOS: Retrieved String '$key' = '${value ?: defaultValue}'")
        return value ?: defaultValue
    }

    actual fun putInt(key: String, value: Int) {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        userDefaults.setInteger(value.toLong(), forKey = key) // NSUserDefaults usa NSInteger (Long em Kotlin)
        userDefaults.synchronize()
        println("iOS: Saved Int '$key' = '$value'")
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        // NSUserDefaults retorna 0 se a chave não existir para getInteger, então verificamos a existência.
        val value = if (userDefaults.objectForKey(key) != null) {
            userDefaults.integerForKey(key).toInt()
        } else {
            defaultValue
        }
        println("iOS: Retrieved Int '$key' = '$value'")
        return value
    }

    actual fun putLong(key: String, value: Long) {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        userDefaults.setInteger(value, forKey = key) // NSUserDefaults usa NSInteger (Long em Kotlin)
        userDefaults.synchronize()
        println("iOS: Saved Long '$key' = '$value'")
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        // NSUserDefaults não tem suporte direto para Long, mas podemos usar Double como alternativa.
        val value = userDefaults.doubleForKey(key)
        val longValue = if (userDefaults.objectForKey(key) != null) {
            userDefaults.integerForKey(key).toLong()
        } else {
            defaultValue
        }
        println("iOS: Retrieved Int '$key' = '$value'")
        return value.toLong()
    }


    actual fun putMap(key: String, value: Map<String, String>) {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        val dict = NSMutableDictionary()
        value.forEach { (k, v) ->
            dict.setObject(v as NSString, forKey = k as NSString)
        }
        userDefaults.setObject(dict, forKey = key)
        userDefaults.synchronize()
        println("iOS: Saved Map '$key' = '$value'")
    }


    actual fun getMap(key: String): Map<String, String> {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        val dict = userDefaults.objectForKey(key) as? NSDictionary
        val result = mutableMapOf<String, String>()
        dict?.let {
            val enumerator = it.keyEnumerator()
            while (true) {
                val nsKey = enumerator.nextObject() as? NSString ?: break
                val nsValue = it.objectForKey(nsKey) as? NSString
                result[nsKey.toString()] = nsValue?.toString() ?: ""
            }
        }
        return result
    }


    actual fun putBoolean(key: String, value: Boolean) {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        userDefaults.setBool(value, forKey = key)
        userDefaults.synchronize()
        println("iOS: Saved Boolean '$key' = '$value'")
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        userDefaults.persistentDomainForName(
            NSBundle.mainBundle.bundleIdentifier ?: "br.com.flemis.bookishadventure.iphone"
        )
        // NSUserDefaults retorna false se a chave não existir para getBool, então verificamos a existência.
        val value = if (userDefaults.objectForKey(key) != null) {
            userDefaults.boolForKey(key)
        } else {
            defaultValue
        }
        println("iOS: Retrieved Boolean '$key' = '$value'")
        return value
    }

    actual fun clearPreferences(key: String?) {
        if (key.isNullOrEmpty()) {
            /* userDefaults.removePersistentDomainForName(NSBundle.mainBundle.bundleIdentifier?:"br.com.flemis.bookishadventure.iphone")
             userDefaults.synchronize()*/
            NSUserDefaults.resetStandardUserDefaults()
            userDefaults.dictionaryRepresentation().keys.forEach { key ->
                userDefaults.removeObjectForKey(key as String)
            }
            userDefaults.synchronize()

            println("iOS: Cleared all preferences")
        } else {
            userDefaults.removeObjectForKey(key)
            userDefaults.synchronize()
            println("iOS: Cleared $key")
        }
    }
}

actual fun getInstancePreferences(): Preferences {
    return Preferences()
}
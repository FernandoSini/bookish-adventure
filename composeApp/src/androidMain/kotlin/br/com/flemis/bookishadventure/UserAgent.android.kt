package br.com.flemis.bookishadventure

import android.content.Context

actual class UserAgent constructor(internal val context: Context){
    actual fun getUserAgent(): String{
        val myApplication = MyApplication()
        System.getProperty("java.vm.name")
        System.getProperty("java.vm.arch")
        return System.getProperty("os.version")
    }
}

actual fun createUserAgentInstance(): UserAgent {
    val myApplicationContext = MyApplication.applicationContext()
    return UserAgent(myApplicationContext)
}
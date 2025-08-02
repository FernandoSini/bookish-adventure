package br.com.flemis.bookishadventure

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import br.com.flemis.bookishadventure.core.di.initKoin
import br.com.flemis.bookishadventure.utils.CustomActivityProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent


class MyApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin {
            androidContext(this@MyApplication)
        }

    }


    companion object {
        private var instance: MyApplication = MyApplication()

        fun applicationContext(): Context {
            return instance.applicationContext
        }
       /* @SuppressLint("ServiceCast")
        fun getActivity(): Activity? {
         return this.instance.getSystemService(Context.ACTIVITY_SERVICE) as Activity?
        }*/

    }


}

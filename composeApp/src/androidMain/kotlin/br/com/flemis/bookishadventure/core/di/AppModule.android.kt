package br.com.flemis.bookishadventure.core.di

import android.content.Context
import br.com.flemis.bookishadventure.data.datasource.local.db.AppDatabase
import br.com.flemis.bookishadventure.data.datasource.local.db.getRoomDatabase
import br.com.flemis.bookishadventure.data.datasource.remote.IapServiceImpl
import br.com.flemis.bookishadventure.utils.PermissionHandler
import br.com.flemis.bookishadventure.utils.Preferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single<AppDatabase> { getRoomDatabase(get()) }
    //singleOf(::IapServiceImpl)
    single<Preferences> { Preferences(get()) }

}

fun androidModule(context: Context) = module {
    single { PermissionHandler(context = context) }
}
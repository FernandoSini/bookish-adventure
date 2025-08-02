package br.com.flemis.bookishadventure.core.di

import br.com.flemis.bookishadventure.data.datasource.remote.IapServiceImpl
import br.com.flemis.bookishadventure.data.datasource.db.getDatabase
import br.com.flemis.bookishadventure.data.datasource.local.db.AppDatabase
import br.com.flemis.bookishadventure.utils.PermissionHandler
import br.com.flemis.bookishadventure.utils.Preferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


actual val platformModule = module {
    single<AppDatabase> { getDatabase() }
    //o problema de build lento estava aqui no ios
    //singleOf(::IapServiceImpl)
    single<Preferences> { Preferences() }
    single<PermissionHandler> { PermissionHandler() }
}
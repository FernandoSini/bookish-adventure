package br.com.flemis.bookishadventure.core.di

import br.com.flemis.bookishadventure.features.auth.domain.usecases.AuthUseCase
import br.com.flemis.bookishadventure.core.domain.usecases.InAppPurchaseUseCase
import br.com.flemis.bookishadventure.features.settings.domain.usecases.ThemeUseCase
import br.com.flemis.bookishadventure.core.domain.usecases.UserUseCase
import br.com.flemis.bookishadventure.data.datasource.local.LocalDataSource
import br.com.flemis.bookishadventure.data.datasource.local.LocalDataSourceImpl
import br.com.flemis.bookishadventure.data.datasource.remote.IapDataSource
import br.com.flemis.bookishadventure.data.datasource.remote.IapDataSourceImpl

import br.com.flemis.bookishadventure.data.datasource.remote.RemoteDataSource
import br.com.flemis.bookishadventure.data.datasource.remote.RemoteDataSourceImpl
import br.com.flemis.bookishadventure.data.datasource.remote.services.AuthService
import br.com.flemis.bookishadventure.data.datasource.remote.services.UserService
import br.com.flemis.bookishadventure.data.datasource.remote.services.implementations.AuthServiceImpl
import br.com.flemis.bookishadventure.data.datasource.remote.services.implementations.UserServiceImpl
import br.com.flemis.bookishadventure.features.auth.domain.repository.AuthRepository
import br.com.flemis.bookishadventure.features.settings.domain.repository.ThemeRepository
import br.com.flemis.bookishadventure.data.repository.UserRepository
import br.com.flemis.bookishadventure.features.auth.data.repository.AuthRepositoryImpl
import br.com.flemis.bookishadventure.features.auth.data.repository.UserRepositoryImpl
import br.com.flemis.bookishadventure.features.settings.data.repository.implementations.ThemeRepositoryImpl
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.AuthViewModel
import br.com.flemis.bookishadventure.features.base.presentation.ui.viewmodel.BaseViewModel
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.BillingViewModel
import br.com.flemis.bookishadventure.features.home.presentation.ui.viewmodel.HomeViewModel
import br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.InAppPurchaseViewModel
import br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.PaywallViewModel
import br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel.SplashViewModel
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.presentation.viewmodel.UserViewModel
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.VerifyCodeViewModel
import br.com.flemis.bookishadventure.features.maps.data.datasource.local.MapLocalDataSource
import br.com.flemis.bookishadventure.features.maps.data.datasource.local.MapLocalDataSourceImpl
import br.com.flemis.bookishadventure.features.maps.data.datasource.remote.MapRemoteDataSource
import br.com.flemis.bookishadventure.features.maps.data.datasource.remote.MapRemoteDataSourceImpl
import br.com.flemis.bookishadventure.features.maps.domain.usecases.MapUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
//import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module


val datasourceModule: Module = module {
    singleOf(::LocalDataSourceImpl).bind(LocalDataSource::class)
    singleOf(::RemoteDataSourceImpl).bind(RemoteDataSource::class)
    singleOf(::IapDataSourceImpl).bind(IapDataSource::class)
    singleOf(::MapLocalDataSourceImpl).bind(MapLocalDataSource::class)
    singleOf(::MapRemoteDataSourceImpl).bind(MapRemoteDataSource::class)

}

val repositoryModule: Module = module {
    singleOf(::AuthRepositoryImpl).bind(AuthRepository::class)
    singleOf(::ThemeRepositoryImpl).bind(ThemeRepository::class)
    singleOf(::UserRepositoryImpl).bind(UserRepository::class)
}

val useCaseModule: Module = module {
    singleOf(::AuthUseCase)
    singleOf(::ThemeUseCase)
    singleOf(::InAppPurchaseUseCase)
    singleOf(::UserUseCase)
    singleOf(::MapUseCase)
}
val serviceModule: Module = module {
    singleOf(::UserServiceImpl).bind(UserService::class)
    singleOf(::AuthServiceImpl).bind(AuthService::class)
}
val viewModelModule: Module = module {
    // Define your ViewModels here if needed
    // single { MyViewModel(get()) }
    viewModelOf(::ThemeViewModel)
    viewModelOf(::InAppPurchaseViewModel)
    viewModelOf(::UserViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::VerifyCodeViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::BaseViewModel)
    viewModelOf(::PaywallViewModel)
    // viewModelOf(::PermissionViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::BillingViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    // Initialize Koin with the app module
    startKoin {
        config?.invoke(this)
        modules(platformModule, datasourceModule, repositoryModule, useCaseModule, serviceModule, viewModelModule)
    }
}
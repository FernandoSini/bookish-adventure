package br.com.flemis.bookishadventure

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import br.com.flemis.bookishadventure.features.app.presentation.pages.App
import br.com.flemis.bookishadventure.core.di.initKoin
import br.com.flemis.bookishadventure.data.datasource.db.getDatabase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

val LocalNativeViewFactory =
    staticCompositionLocalOf<NativeViewFactory> { error("No factory Found") }

@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
fun mainViewController(nativeViewFactory: NativeViewFactory) =
    ComposeUIViewController(
        configure = {
            initKoin()
            /* onFocusBehavior = OnFocusBehavior.DoNothing*/
            enforceStrictPlistSanityCheck = false
            opaque = false
        }
    ) {
       Napier.base(DebugAntilog())
        //val iapManager = IapServiceImpl()
        val database = remember { getDatabase() }
        CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
            App()
        }
    }
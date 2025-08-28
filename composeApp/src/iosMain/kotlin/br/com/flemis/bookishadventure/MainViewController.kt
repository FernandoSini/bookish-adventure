@file:Suppress(
    "INVISIBLE_MEMBER",
    "INVISIBLE_REFERENCE",
    "FINAL_SUPERTYPE",
    "EXPOSED_SUPER_CLASS",
    "UNRESOLVED_REFERENCE",
    "CANNOT_OVERRIDE_INVISIBLE_MEMBER",
    "NOTHING_TO_OVERRIDE",
    "ABSTRACT_MEMBER_NOT_IMPLEMENTED",
)
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
import platform.UIKit.UIColor

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
       Napier.base(DebugAntilog(coroutinesSuffix = false, defaultTag = "Bookish-Adventure"),)
        //val iapManager = IapServiceImpl()
     //   val database = remember { getDatabase() }
        CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
            App()
        }
    }.apply { view.backgroundColor = UIColor(white = 0.0, alpha = 0.0) }
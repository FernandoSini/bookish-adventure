package br.com.fernandosini.bookishadventure

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import br.com.fernandosini.bookishadventure.screens.App

@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
fun MainViewController() =
    ComposeUIViewController(configure = {
       /* onFocusBehavior = OnFocusBehavior.DoNothing*/
        enforceStrictPlistSanityCheck = false
        opaque = false
    }) {
        val database = remember { createRoomDatabase() }
        App(database)
    }
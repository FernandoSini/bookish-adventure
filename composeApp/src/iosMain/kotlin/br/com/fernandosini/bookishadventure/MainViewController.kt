package br.com.fernandosini.bookishadventure

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import br.com.fernandosini.bookishadventure.screens.App

@OptIn(ExperimentalComposeApi::class)
fun MainViewController() =
    ComposeUIViewController(configure = {
        enforceStrictPlistSanityCheck = false
        opaque = false
    }) {
        val database = remember { createRoomDatabase() }
        App(database)
    }
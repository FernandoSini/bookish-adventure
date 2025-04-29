package br.com.fernandosini.bookishadventure.screens.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.lifecycle.ViewModel
import bookishadventure.composeapp.generated.resources.Home
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.account
import bookishadventure.composeapp.generated.resources.flights
import bookishadventure.composeapp.generated.resources.plane_filled
import bookishadventure.composeapp.generated.resources.search
import kotlinx.coroutines.flow.MutableStateFlow

class BaseViewModel : ViewModel() {
    val bottomMenuItems: MutableStateFlow<List<MutableMap<Any?, Any?>>>;

    var bottomIndex: MutableStateFlow<Int>;

    init {
        bottomMenuItems = MutableStateFlow<List<MutableMap<Any?, Any?>>>(
            listOf(
                mutableMapOf
                    (
                    "screen" to "home",
                    "icon" to Icons.Default.Home,
                    "label" to Res.string.Home,
                ),
                mutableMapOf(
                    "screen" to "flights",
                    "icon" to Res.drawable.plane_filled,
                    "label" to Res.string.flights
                ),
                mutableMapOf(
                    "screen" to "search",
                    "icon" to Icons.Default.Search,
                    "label" to Res.string.search
                ),
                mutableMapOf(
                    "screen" to "account",
                    "icon" to Icons.Default.AccountCircle,
                    "label" to Res.string.account
                ),

                )
        )

        bottomIndex = MutableStateFlow<Int>(0)
    }
}
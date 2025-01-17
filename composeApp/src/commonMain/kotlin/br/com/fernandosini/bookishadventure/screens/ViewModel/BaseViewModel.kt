package br.com.fernandosini.bookishadventure.screens.ViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.lifecycle.ViewModel
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
                    "label" to "Home",
                    ),
                mutableMapOf(
                    "screen" to "favorite",
                    "icon" to Icons.Default.Favorite,
                    "label" to "Favorites"
                ),
                mutableMapOf(
                    "screen" to "search",
                    "icon" to Icons.Default.Search,
                    "label" to "Search"
                ),
                mutableMapOf(
                    "screen" to "account",
                    "icon" to Icons.Default.AccountCircle,
                    "label" to "profile"
                ),

                )
        )

        bottomIndex = MutableStateFlow<Int>(0)
    }
}
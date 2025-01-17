package br.com.fernandosini.bookishadventure.screens.ViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    val listMenuChips: MutableStateFlow<List<String>>;
    var selectedChip: MutableStateFlow<String>;


    init {
        listMenuChips = MutableStateFlow<List<String>>(
            listOf(
                "All",
                "Recommended",
                "Most Rated",
                "New",
            )
        )
        selectedChip = MutableStateFlow<String>("All")

    }


}
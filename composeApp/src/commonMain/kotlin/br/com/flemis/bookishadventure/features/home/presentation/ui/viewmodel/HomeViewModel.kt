package br.com.flemis.bookishadventure.features.home.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
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
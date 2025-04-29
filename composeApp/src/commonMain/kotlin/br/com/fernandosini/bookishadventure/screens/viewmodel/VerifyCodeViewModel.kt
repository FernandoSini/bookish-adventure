package br.com.fernandosini.bookishadventure.screens.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fernandosini.bookishadventure.screens.viewmodel.states.VerifyCodeState
import kotlinx.coroutines.flow.MutableStateFlow
class VerifyCodeViewModel:ViewModel() {

    private val _state  = MutableStateFlow(VerifyCodeState())
}
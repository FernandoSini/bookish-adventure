package br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state.VerifyCodeState
import kotlinx.coroutines.flow.MutableStateFlow

class VerifyCodeViewModel: ViewModel() {

    private val _state  = MutableStateFlow(VerifyCodeState())
}
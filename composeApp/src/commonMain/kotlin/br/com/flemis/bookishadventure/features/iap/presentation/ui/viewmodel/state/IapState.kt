package br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.state

import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.core.errors.states.ErrorResponseState

data class IapState(
    var isLoading: Boolean = false,
    var isPurchased: PurchaseResultModel = PurchaseResultModel.Pending,
    var isRestored: Boolean = false,
    var errorResponseState: ErrorResponseState? = ErrorResponseState(),)
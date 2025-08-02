package br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.core.domain.usecases.InAppPurchaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InAppPurchaseViewModel : ViewModel(), KoinComponent {

    private val inAppPurchaseUseCase: InAppPurchaseUseCase by inject()

    private val _purchaseState = MutableStateFlow<PurchaseResultModel?>(null);
    var purchaseState: StateFlow<PurchaseResultModel?> =
        _purchaseState.onStart {
            inAppPurchaseUseCase.initializeIapManager(_purchaseState)
        }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(), null)


    private suspend fun purchaseUpdates() {
        inAppPurchaseUseCase.collectUpdates(viewModelScope, _purchaseState)
    }
    private suspend fun makePurchase(product: ProductDetailsModel): Result<Unit> {
        return inAppPurchaseUseCase.purchaseProduct(product)
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            inAppPurchaseUseCase.dispose()
        }
    }


}
package br.com.flemis.bookishadventure.core.domain.usecases


import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.data.datasource.remote.IapDataSource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InAppPurchaseUseCase : KoinComponent {
    private val iapDataSource: IapDataSource by inject()
    suspend fun initializeIapManager(purchaseState: MutableStateFlow<PurchaseResultModel?>) {
        iapDataSource.initialize { result ->
            when (result) {
                is PurchaseResultModel.Success -> {
                  //  Napier.i { "IapUseCase: Success: ${result.purchase}" }
                }

                is PurchaseResultModel.Error -> {
                   // Napier.e { "IapUsecase: Error on IAp ${result.message}" }
                }

                is PurchaseResultModel.Pending -> {

                }
                is PurchaseResultModel.UserCancelled -> {}
            }
            /*purchaseState.value = result
            println("IapViewModel: Purchase update received via callback: $result")*/
        }


    }

    suspend fun collectUpdates(viewModelScope: CoroutineScope, purchaseState: MutableStateFlow<PurchaseResultModel?>) {
        viewModelScope.launch {
            iapDataSource.purchaseUpdates.collect { result ->
                purchaseState.value = result
                println("IapViewModel: Purchase update received via SharedFlow: $result")
            }
        }
    }

    suspend fun purchaseProduct(productDetails: ProductDetailsModel): Result<Unit> {
        return try {
            iapDataSource.makePurchase(productDetails.productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restorePurchases(): Result<Unit> {
        return try {
            iapDataSource.restorePurchases()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun dispose() = iapDataSource.dispose()


}
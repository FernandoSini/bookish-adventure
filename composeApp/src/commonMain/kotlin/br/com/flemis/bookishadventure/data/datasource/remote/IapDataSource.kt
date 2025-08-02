package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.data.datasource.remote.services.RequestService
import kotlinx.coroutines.flow.SharedFlow

interface IapDataSource: RequestService {
    suspend fun initialize(onPurchaseUpdate: (PurchaseResultModel) -> Unit)
    suspend fun queryProducts(productIds: List<String>): List<ProductDetailsModel>
    suspend fun makePurchase(productId: String)
    suspend fun restorePurchases()
    val purchaseUpdates: SharedFlow<PurchaseResultModel>
    suspend fun getActivePurchases(): List<PurchaseModel>
    suspend fun dispose()

}
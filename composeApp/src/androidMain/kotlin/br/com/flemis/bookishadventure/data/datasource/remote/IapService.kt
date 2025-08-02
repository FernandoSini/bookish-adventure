/*
package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.ProductDetails
import br.com.flemis.bookishadventure.core.domain.models.Purchase
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResult
import kotlinx.coroutines.flow.SharedFlow

actual interface IapService {
    actual fun initialize(onPurchaseUpdate: (PurchaseResult) -> Unit)
    actual suspend fun queryProducts(productIds: List<String>): List<ProductDetails>
    actual fun makePurchase(product: ProductDetails)
    actual fun restorePurchases()
    actual val purchaseUpdates: SharedFlow<PurchaseResult>
    actual suspend fun getActivePurchases(): List<Purchase>
    actual fun dispose()
}*/

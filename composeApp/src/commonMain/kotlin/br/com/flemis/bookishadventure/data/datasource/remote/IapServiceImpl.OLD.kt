/*
package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.ProductDetails
import br.com.flemis.bookishadventure.core.domain.models.Purchase
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResult
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

expect class IapServiceImpl : IapService {


    override fun initialize(onPurchaseUpdate: (PurchaseResult) -> Unit)

    override suspend fun queryProducts(productIds: List<String>): List<ProductDetails>

    override fun makePurchase(product: ProductDetails)

    override fun restorePurchases()

    override val purchaseUpdates: SharedFlow<PurchaseResult>

    override suspend fun getActivePurchases(): List<Purchase>

    override fun dispose()
}*/

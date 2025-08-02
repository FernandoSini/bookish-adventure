package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IapDataSourceImpl : IapDataSource, KoinComponent {
    private val iapService: IapServiceImpl by inject()

    override suspend fun initialize(onPurchaseUpdate: (PurchaseResultModel) -> Unit) =
        iapService.initialize(onPurchaseUpdate)


    override suspend fun queryProducts(productIds: List<String>) = iapService.queryProducts(productIds)

    override suspend fun makePurchase(productId: String) {
        val product = iapService.queryProducts(listOf(productId)).firstOrNull()
        if (product != null) {
            iapService.makePurchase(product)
        } else {
            throw IllegalArgumentException("Product not found: $productId")
        }
    }

    override suspend fun restorePurchases() = iapService.restorePurchases()


    override val purchaseUpdates = iapService.purchaseUpdates

    override suspend fun getActivePurchases() = iapService.getActivePurchases()

    override suspend fun dispose() = iapService.dispose()


}
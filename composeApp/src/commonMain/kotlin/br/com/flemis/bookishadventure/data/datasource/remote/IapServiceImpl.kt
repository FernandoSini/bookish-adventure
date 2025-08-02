package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import kotlinx.coroutines.flow.SharedFlow

/**
 * Interface para gerenciar compras in-app de forma multiplataforma.
 */
expect class IapServiceImpl {
    /**
     * Inicializa o serviço de IAP. Deve ser chamado antes de qualquer outra operação.
     * @param onPurchaseUpdate Um callback para receber atualizações de compras (sucedidas, pendentes, falhas).
     */
    fun initialize(onPurchaseUpdate: (PurchaseResultModel) -> Unit)

    /**
     * Consulta os detalhes de produtos in-app a partir de uma lista de IDs de produtos.
     * @param productIds Lista de IDs de produtos a serem consultados.
     * @return Lista de [br.com.flemis.bookishadventure.core.domain.models.ProductDetails] dos produtos encontrados.
     */
    suspend fun queryProducts(productIds: List<String>): List<ProductDetailsModel>

    /**
     * Inicia o fluxo de compra para um produto específico.
     * @param product O [ProductDetails] do produto a ser comprado.
     */
    fun makePurchase(product: ProductDetailsModel)

    /**
     * Restaura compras anteriores para produtos não consumíveis ou assinaturas.
     * O resultado será entregue via o `onPurchaseUpdate` do initialize.
     */
    fun restorePurchases()

    /**
     * Retorna um fluxo que emite atualizações de compras.
     * Útil para observar o status de compras de qualquer lugar.
     */
    val purchaseUpdates: SharedFlow<PurchaseResultModel> // Use SharedFlow para eventos

    /**
     * Retorna a lista de compras ativas conhecidas.
     * Útil para verificar o entitlement inicial após o lançamento do app.
     */
    suspend fun getActivePurchases(): List<PurchaseModel>


    fun dispose() // Libera recursos e para o serviço de IAP
}
package br.com.flemis.bookishadventure.data.datasource.remote

import android.app.Activity
import android.content.Context
import android.util.Log
import br.com.flemis.bookishadventure.config.KtorClient
import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.ProductType
import br.com.flemis.bookishadventure.core.domain.models.PurchaseModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.data.datasource.remote.NativeRequestService
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume

// Use um singleton para o BillingClient e IapManager
actual class IapServiceImpl(
    private val context: Context
) :
    PurchasesUpdatedListener,
    BillingClientStateListener {

    private lateinit var billingClient: BillingClient
    private var onPurchaseUpdateCallback: ((PurchaseResultModel) -> Unit)? = null
    private val _purchaseUpdates = MutableSharedFlow<PurchaseResultModel>(extraBufferCapacity = 1)
    actual val purchaseUpdates: SharedFlow<PurchaseResultModel> = _purchaseUpdates

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    actual fun initialize(onPurchaseUpdate: (PurchaseResultModel) -> Unit) {
        this.onPurchaseUpdateCallback = onPurchaseUpdate
        billingClient = BillingClient.newBuilder(context)
            .setListener(this).setListener(this).enableUserChoiceBilling {
                // Example: Log when user choice billing is enabled
                Log.d("IapServiceImpl", "User choice billing enabled")
                it.products.forEach { product ->
                    Log.d("IapServiceImpl", "Product available: ${product.id}")
                }

                // Example: Set a feature flag or call custom logic
                // featureManager.enableAlternativeBilling()

                // You can also perform analytics or other setup here
            }
            /* .enablePendingPurchases(
                     // Se você não precisa de compras pendentes, pode omitir isso
                     // Se precisar, configure corretamente com o seu backend
                     // Exemplo: PendingPurchasesParams.newBuilder().setPendingPurchasesEnabled(true).build()
                 PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()

             )*/
            .build()

        billingClient.startConnection(this)
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            // Cliente de billing conectado com sucesso
            // Você pode querer consultar compras ativas aqui ou em getActivePurchases
            coroutineScope.launch {
                queryActivePurchasesInternal()
            }
        } else {
            // Lidar com erro de conexão
            _purchaseUpdates.tryEmit(
                PurchaseResultModel.Error(
                    "Falha na conexão com Google Play: ${billingResult.debugMessage}",
                    billingResult.responseCode
                )
            )
        }
    }

    override fun onBillingServiceDisconnected() {
        // Tentar reconectar ou informar ao usuário
        _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Serviço do Google Play desconectado", null))
        billingClient.startConnection(this) // Tenta reconectar automaticamente
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.UserCancelled)
            _purchaseUpdates.tryEmit(PurchaseResultModel.UserCancelled)
        } else {
            onPurchaseUpdateCallback?.invoke(
                PurchaseResultModel.Error(
                    "Erro na compra: ${billingResult.debugMessage}",
                    billingResult.responseCode
                )
            )
            _purchaseUpdates.tryEmit(
                PurchaseResultModel.Error(
                    "Erro na compra: ${billingResult.debugMessage}",
                    billingResult.responseCode
                )
            )
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // **IMPORTANTE: VALIDAÇÃO DO RECIBO COM SEU BACKEND AQUI**
            // Envie purchase.purchaseToken para seu backend para validação com a API do Google Play Developer.
            // Se a validação for bem-sucedida, conceda o entitlement ao usuário.
            // Send purchaseToken to your backend for validation
            var result: Result<String> = Result.failure(Exception("Not yet validated"));
            coroutineScope.launch {
                result = sendReceiptToBackend(purchase)
            }

            if (result.isSuccess) {
                val commonPurchase = PurchaseModel(
                    productId = purchase.products.first(), // Assumindo 1 produto por compra
                    purchaseToken = purchase.purchaseToken,
                    purchaseTime = purchase.purchaseTime,
                    orderId = purchase.orderId,
                    quantity = purchase.quantity,
                    isAcknowledged = purchase.isAcknowledged
                )

                onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Success(commonPurchase))
                _purchaseUpdates.tryEmit(PurchaseResultModel.Success(commonPurchase))

                // Se for consumível, deve ser ACKNOWLEDGED e CONSUMIDO (consumePurchase)
                // Se for não consumível ou assinatura, deve ser ACKNOWLEDGED (acknowledgePurchase)
                if (!purchase.isAcknowledged) {
                    acknowledgePurchase(purchase)
                } else {
                    // Se for consumível, consome a compra
                    if (purchase.products.firstOrNull()?.contains("consumable") == true) {
                        consumePurchase(purchase)
                    }
                }
            } else if (result.isFailure) {
                onPurchaseUpdateCallback?.invoke(
                    PurchaseResultModel.Error(
                        "Erro ao validar recibo: ${result.exceptionOrNull()?.message}",
                        null
                    )
                )
                _purchaseUpdates.tryEmit(
                    PurchaseResultModel.Error(
                        "Erro ao validar recibo: ${result.exceptionOrNull()?.message}",
                        null
                    )
                )
            }

        } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
            // Compra pendente (ex: pagamento aguardando confirmação)
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Pending)
            _purchaseUpdates.tryEmit(PurchaseResultModel.Pending)
        } else if (purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            // Estado desconhecido
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Error("Estado de compra desconhecido", null))
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Estado de compra desconhecido", null))
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Compra reconhecida com sucesso
            } else {
                // Lidar com erro no reconhecimento
                // Isso é crítico, pois se não for reconhecido, o Google pode estornar a compra.
            }
        }
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Item consumido com sucesso. Pode ser comprado novamente.
                _purchaseUpdates.tryEmit(
                    PurchaseResultModel.Success(
                        PurchaseModel(
                            productId = purchase.products.firstOrNull() ?: "unknown",
                            purchaseToken = purchase.purchaseToken,
                            purchaseTime = purchase.purchaseTime,
                            orderId = purchase.orderId,
                            quantity = purchase.quantity,
                            isAcknowledged = purchase.isAcknowledged
                        )
                    )
                )

            } else {
                // Lidar com erro no consumo
                _purchaseUpdates.tryEmit(
                    PurchaseResultModel.Error(
                        "Erro ao consumir compra: ${billingResult.debugMessage}",
                        billingResult.responseCode
                    )
                )
            }
        }
    }


    actual suspend fun queryProducts(productIds: List<String>): List<ProductDetailsModel> =
        suspendCancellableCoroutine { continuation ->
            val productList = productIds.map { id ->
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(id)
                    .setProductType(BillingClient.ProductType.INAPP) // OU BillingClient.ProductType.SUBS
                    .build()
            }

            val params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build()

            billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val mappedProducts = productDetailsList.map { p ->
                        ProductDetailsModel(
                            productId = p.productId,
                            name = p.title,
                            description = p.description,
                            // Para IAP, pega o preço base. Para assinaturas é mais complexo.
                            price = p.oneTimePurchaseOfferDetails?.formattedPrice ?: "N/A",
                            currencyCode = p.oneTimePurchaseOfferDetails?.priceCurrencyCode ?: "N/A",
                            priceAmountMicros = p.oneTimePurchaseOfferDetails?.priceAmountMicros ?: 0L,
                            type = when (p.productType) {
                                BillingClient.ProductType.INAPP -> ProductType.NON_CONSUMABLE // Ou CONSUMABLE
                                BillingClient.ProductType.SUBS -> ProductType.SUBSCRIPTION
                                else -> ProductType.NON_CONSUMABLE // Default ou erro
                            }
                        )
                    }
                    continuation.resume(mappedProducts)
                    saveAndroidProductDetails(mappedProducts)
                } else {
                    // Lidar com erro na consulta
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Erro na consulta de produtos: ${billingResult.debugMessage}",
                            billingResult.responseCode
                        )
                    )
                    continuation.resume(emptyList()) // Ou throw exception
                }
            }
        }

    actual fun makePurchase(product: ProductDetailsModel) {
        if (context !is Activity) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Contexto deve ser uma Activity para iniciar a compra."))
            return
        }

        // Você precisará ter o ProductDetails original do Google Play Billing Library para iniciar a compra
        // Para simplificar, neste exemplo, você precisaria ter mapeado o ProductDetails original.
        // O ideal seria que queryProducts retornasse um objeto que inclua o ProductDetails nativo
        // para ser usado aqui.
        // Por exemplo, você pode criar um mapa de productId para ProductDetails original
        // ou refatorar ProductDetails para incluir o objeto nativo.

        // Exemplo simplificado (assumindo que você tem o ProductDetails original de alguma forma)
        // Você precisará obter o ProductDetails original a partir do ID do produto
        // para construir o BillingFlowParams.
        // Isso é um dos desafios de usar expect/actual com APIs nativas complexas.


        // Neste esboço, vamos simular como seria se tivéssemos o ProductDetails original:
        val productDetailsToPurchase: ProductDetails? =
            skProductsAndroid[product.productId] as? ProductDetails // PRECISA SER O OBJETO NATIVO

        if (productDetailsToPurchase == null) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Detalhes do produto nativo não encontrados para a compra."))
            return
        }

        val offerToken =
            productDetailsToPurchase.subscriptionOfferDetails?.firstOrNull()?.offerToken // Para assinaturas

        if (offerToken == null) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Nenhum token de oferta válido encontrado para o produto."))
            return
        }


        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetailsToPurchase)
            .setOfferToken(offerToken)
            .build()

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        billingClient.launchBillingFlow(context, billingFlowParams)
    }

    actual fun restorePurchases() {
        coroutineScope.launch {
            queryActivePurchasesInternal() // A restauração é basicamente consultar as compras ativas.
        }
    }

    actual suspend fun getActivePurchases(): List<PurchaseModel> {
        return queryActivePurchasesInternal()
    }

    private suspend fun queryActivePurchasesInternal(): List<PurchaseModel> =
        suspendCancellableCoroutine { continuation ->
            if (!billingClient.isReady) {
                // Tentar reconectar ou lidar com o caso de cliente não pronto
                continuation.resume(emptyList())
                return@suspendCancellableCoroutine
            }

            val inAppParams = QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()

            val subParams = QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()

            // Consulta compras in-app
            billingClient.queryPurchasesAsync(inAppParams) { inAppBillingResult, inAppPurchaseList ->
                if (inAppBillingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Consulta compras de assinatura
                    billingClient.queryPurchasesAsync(subParams) { subBillingResult, subPurchaseList ->
                        if (subBillingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            val allPurchases =
                                mutableListOf<PurchaseModel>()
                            (inAppPurchaseList + subPurchaseList).forEach { purchase ->
                                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                    allPurchases.add(
                                        PurchaseModel(
                                            productId = purchase.products.firstOrNull() ?: "unknown",
                                            purchaseToken = purchase.purchaseToken,
                                            purchaseTime = purchase.purchaseTime,
                                            orderId = purchase.orderId,
                                            quantity = purchase.quantity,
                                            isAcknowledged = purchase.isAcknowledged
                                        )
                                    )
                                    if (!purchase.isAcknowledged) {
                                        acknowledgePurchase(purchase) // Reconhecer compras não reconhecidas
                                    }
                                }
                            }
                            continuation.resume(allPurchases)
                        } else {
                            _purchaseUpdates.tryEmit(
                                PurchaseResultModel.Error(
                                    "Erro ao consultar assinaturas: ${subBillingResult.debugMessage}",
                                    subBillingResult.responseCode
                                )
                            )
                            continuation.resume(emptyList())
                        }
                    }
                } else {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Erro ao consultar compras in-app: ${inAppBillingResult.debugMessage}",
                            inAppBillingResult.responseCode
                        )
                    )
                    continuation.resume(emptyList())
                }
            }
        }

    actual fun dispose() {
        if (billingClient.isReady) {
            billingClient.endConnection()
            println("Android: Google Play Billing desconectado.")
        }
        coroutineScope.cancel()
    }

    // Mapeamento para guardar os ProductDetails nativos (Google Play Billing)
    // Isso é necessário para iniciar a compra corretamente.
    private var skProductsAndroid: Map<String, ProductDetails> = emptyMap()

    // Este método é chamado na consulta de produtos para salvar os detalhes nativos.
    // Você pode integrar isso no `queryProducts` para salvar os objetos `ProductDetails`
    // do Google Play.
    private fun saveAndroidProductDetails(productDetailsList: List<ProductDetailsModel>) {
        skProductsAndroid =
            (productDetailsList as List<ProductDetails>).associateBy { it.productId }
    }

    private suspend fun sendReceiptToBackend(purchase: Purchase): Result<String> {
        // Aqui você enviaria o purchase.purchaseToken para o seu backend
        // para validação com a API do Google Play Developer.
        // Exemplo:
        // backendApi.validatePurchase(purchase.purchaseToken)

        Log.d("IapServiceImpl", "Enviando recibo para o backend: ${purchase.purchaseToken}")

        try {
            val body = mapOf(
                "purchaseToken" to purchase.purchaseToken,
                "productId" to purchase.products.first(),
                "orderId" to purchase.orderId.toString()
            )

            val response = NativeRequestService.instance.post("", body)

            if (response.status != HttpStatusCode.OK) {
                Log.e("IapServiceImpl", "Receipt validation failed: ${response.body<String>()}")
                return Result.failure(Exception("Receipt validation failed: ${response.body<String>()}"))
            } else {
                Log.d("IapServiceImpl", "Receipt validated successfully: ${response.body<String>()}")
                return Result.success(response.body())
            }
        } catch (e: Exception) {
            Log.e("IapServiceImpl", "Error validating receipt: ${e.message}")
            return Result.failure(Exception("Error validating receipt: ${e.message}"))
        }

    }
}

class NativeRequestService {
    suspend fun get(url: String): HttpResponse {
        val response = KtorClient.httpClient.get(url) {
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun post(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.post {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun put(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.put {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun delete(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.delete {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }
    companion object {
        val instance: NativeRequestService by lazy { NativeRequestService() }
    }
}
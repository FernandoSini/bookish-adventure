package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.ProductDetailsModel
import br.com.flemis.bookishadventure.core.domain.models.ProductType
import br.com.flemis.bookishadventure.core.domain.models.PurchaseModel
import br.com.flemis.bookishadventure.core.domain.models.PurchaseResultModel
import br.com.flemis.bookishadventure.data.datasource.remote.NativeRequestService
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.base64EncodedDataWithOptions
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.base64Encoding
import platform.Foundation.currencyCode
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.timeIntervalSince1970
import platform.StoreKit.SKErrorCode
import platform.StoreKit.SKPayment
import platform.StoreKit.SKPaymentQueue
import platform.StoreKit.SKPaymentTransaction
import platform.StoreKit.SKPaymentTransactionObserverProtocol
import platform.StoreKit.SKPaymentTransactionState
import platform.StoreKit.SKProduct
import platform.StoreKit.SKProductsRequest
import platform.StoreKit.SKProductsRequestDelegateProtocol
import platform.StoreKit.SKProductsResponse
import platform.StoreKit.SKRequest
import platform.darwin.NSObject

// Implementação nativa do StoreKit para iOS
// Necessita de um SKPaymentQueueObserver para lidar com transações
actual class IapServiceImpl : NSObject(), SKProductsRequestDelegateProtocol,
    SKPaymentTransactionObserverProtocol {

    private var onPurchaseUpdateCallback: ((PurchaseResultModel) -> Unit)? = null
    private val _purchaseUpdates = MutableSharedFlow<PurchaseResultModel>(extraBufferCapacity = 1)
    actual val purchaseUpdates: SharedFlow<PurchaseResultModel> = _purchaseUpdates

    private val coroutineScope = CoroutineScope(Dispatchers.Main) // StoreKit callbacks no main thread

    // Mapeamento de SKProduct para nossos ProductDetails
    private var skProducts: Map<String, SKProduct> = emptyMap()

    actual fun initialize(onPurchaseUpdate: (PurchaseResultModel) -> Unit) {
        this.onPurchaseUpdateCallback = onPurchaseUpdate
        SKPaymentQueue.Companion.defaultQueue().addTransactionObserver(this)
    }

    actual suspend fun queryProducts(productIds: List<String>): List<ProductDetailsModel> =
        suspendCancellableCoroutine { continuation ->
            val productsRequest = SKProductsRequest(productIdentifiers = productIds.toSet())
            productsRequest.delegate = object : NSObject(), SKProductsRequestDelegateProtocol {
                override fun productsRequest(request: SKProductsRequest, didReceiveResponse: SKProductsResponse) {
                    val foundProducts = didReceiveResponse.products as List<SKProduct>
                    skProducts = foundProducts.associateBy { it.productIdentifier } // Salva os produtos nativos
                    val mappedProducts = foundProducts.map { skProduct ->
                        val formatter = NSNumberFormatter()
                        formatter.numberStyle = NSNumberFormatterDecimalStyle
                        formatter.locale = skProduct.priceLocale
                        val formattedPrice = formatter.stringFromNumber(skProduct.price) ?: ""

                        // StoreKit não dá `priceAmountMicros` diretamente. Precisa converter de NSDecimalNumber
                        val priceAmountMicros = (skProduct.price.doubleValue * 1_000_000).toLong()

                        ProductDetailsModel(
                            productId = skProduct.productIdentifier,
                            name = skProduct.localizedTitle,
                            description = skProduct.localizedDescription,
                            price = formattedPrice,
                            currencyCode = skProduct.priceLocale.currencyCode ?: "N/A",
                            priceAmountMicros = priceAmountMicros,
                            type = ProductType.NON_CONSUMABLE // StoreKit não diferencia tipo diretamente aqui, você precisa gerenciar isso.
                            // Para assinaturas, o SKProduct terá mais detalhes.
                        )
                    }
                    continuation.resumeWith(Result.success(mappedProducts))
                }

                override fun request(request: SKRequest, didFailWithError: NSError) {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Erro na consulta de produtos iOS: ${didFailWithError.localizedDescription}",
                            didFailWithError.code.toInt()
                        )
                    )
                    //continuation.cancel()
                    continuation.resumeWith(Result.success(emptyList())) // Ou throw exception
                }
            }
            productsRequest.start()
        }

    actual fun makePurchase(product: ProductDetailsModel) {
        val skProduct = skProducts[product.productId]
        if (skProduct != null) {
            val payment = SKPayment.Companion.paymentWithProduct(skProduct)
            SKPaymentQueue.Companion.defaultQueue().addPayment(payment)

        } else {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Produto iOS não encontrado para compra: ${product.productId}"))
        }
    }

    actual fun restorePurchases() {
        SKPaymentQueue.Companion.defaultQueue().restoreCompletedTransactions()
        // O resultado será através de updatedTransactions com estado SKPaymentTransactionStateRestored
    }

    actual suspend fun getActivePurchases(): List<PurchaseModel> {
        // No iOS, "compras ativas" são as transações que o StoreKit conhece e que ainda não foram finalizadas
        // ou as transações restauradas. A maneira mais confiável de verificar entitlements
        // é validar o recibo do aplicativo (app receipt) com seu backend.
        // Este método seria mais complexo para implementar de forma *direta* no cliente iOS.
        // O ideal é que após a inicialização e restauração de compras, você valide
        // o recibo do aplicativo (App Receipt) com seu backend.
        // Para este esboço, vamos retornar as transações PURCHASES e RESTORED que ainda estão na fila
        // e que seu aplicativo pode ter que gerenciar.
        val activeTransactions =
            SKPaymentQueue.Companion.defaultQueue().transactions.filterIsInstance<SKPaymentTransaction>()
                .filter { it.transactionState == SKPaymentTransactionState.SKPaymentTransactionStatePurchased || it.transactionState == SKPaymentTransactionState.SKPaymentTransactionStateRestored }

        return activeTransactions.mapNotNull { it.toCommonPurchase() }
    }


    // SKPaymentQueueObserverProtocol
    private suspend fun paymentQueue(queue: SKPaymentQueue, updatedTransactions: List<SKPaymentTransaction>) {
        updatedTransactions.forEach { transaction ->
            when (transaction.transactionState) {
                SKPaymentTransactionState.SKPaymentTransactionStatePurchasing -> {
                    // Transação em progresso
                    _purchaseUpdates.tryEmit(PurchaseResultModel.Pending)
                }

                SKPaymentTransactionState.SKPaymentTransactionStatePurchased -> {
                    handlePurchasedTransaction(transaction)
                }

                SKPaymentTransactionState.SKPaymentTransactionStateFailed -> {
                    handleFailedTransaction(transaction)
                }

                SKPaymentTransactionState.SKPaymentTransactionStateRestored -> {
                    handleRestoredTransaction(transaction)
                }

                SKPaymentTransactionState.SKPaymentTransactionStateDeferred -> {
                    // Transação pendente de aprovação (ex: ask to buy)
                    _purchaseUpdates.tryEmit(PurchaseResultModel.Pending)
                }

                else -> {
                    // Estado desconhecido
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Estado de transação iOS desconhecido: ${transaction.transactionState}",
                            null
                        )
                    )
                }
            }
        }
    }

    private suspend fun handlePurchasedTransaction(transaction: SKPaymentTransaction) {
        val commonPurchase = transaction.toCommonPurchase()
        if (commonPurchase != null) {
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Success(commonPurchase))
            _purchaseUpdates.tryEmit(PurchaseResultModel.Success(commonPurchase))

            // **IMPORTANTE: VALIDAÇÃO DO RECIBO COM SEU BACKEND AQUI**
            // Você precisa enviar o recibo do aplicativo (receipt) ou o purchase token para seu backend.
            // Para obter o recibo do aplicativo: platform.Foundation.NSBundle.mainBundle.appStoreReceiptURL
            // O backend validará com a API de verificação de recibos da Apple.
            // Envie o recibo do aplicativo para o backend e valide antes de finalizar a transação
            // Exemplo:
            // val receiptUrl = platform.Foundation.NSBundle.mainBundle.appStoreReceiptURL
            // val receiptData = NSData.dataWithContentsOfURL(receiptUrl)
            // Envie receiptData.base64EncodedStringWithOptions(0) para seu backend para validação

            val receiptUrl = platform.Foundation.NSBundle.mainBundle.appStoreReceiptURL
            if (receiptUrl != null) {
                val receiptData = NSData.dataWithContentsOfURL(receiptUrl)
                receiptData?.base64EncodedDataWithOptions(0u)?.let { base64Receipt ->
                    // Aqui você enviaria o base64Receipt para seu backend para validação
                    // Exemplo: enviar para o backend e aguardar a confirmação do entitlement
                    // Seu backend deve validar o recibo com a API de verificação de recibos da Apple
                    // e retornar se o entitlement foi concedido ou não.

                    //parei aqui
                    /*suspend fun sendReceiptToBackend(base64Receipt: String): HttpResponse {
                        val client = HttpClient()
                        return client.post("https://seu-backend.com/validate-receipt") {
                            contentType(ContentType.Application.Json)
                            setBody("""{"receipt":"$base64Receipt"}""")
                        }
                    }*/

                    val response = sendReceiptToBackend(base64Receipt.base64Encoding()).fold(
                        onSuccess = {
                            // Sucesso ao enviar o recibo, o backend deve retornar se o entitlement foi concedido
                            println("iOS: Recibo enviado com sucesso para o backend. Response: $it")
                            // Aqui você pode emitir um evento de sucesso ou atualizar o estado do aplicativo
                            // ou qualquer outra lógica que você precise

                        },
                        onFailure = { error ->
                            // Falha ao enviar o recibo, trate o erro adequadamente
                            _purchaseUpdates.tryEmit(
                                PurchaseResultModel.Error(
                                    "Falha ao enviar recibo para o backend: ${error.message}",
                                    null
                                )
                            )
                        }
                    )

                    println(
                        "iOS: Receipt enviado para o backend para validação: ${
                            base64Receipt.base64EncodedStringWithOptions(
                                0u
                            )
                        }"
                    )
                } ?: run {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Falha ao obter recibo do aplicativo.",
                            null
                        )
                    )
                }

            }

            // Finalizar a transação APENAS depois que o backend confirmar o entitlement
            SKPaymentQueue.Companion.defaultQueue().finishTransaction(transaction)
        } else {
            _purchaseUpdates.tryEmit(
                PurchaseResultModel.Error(
                    "Falha ao mapear transação iOS para Purchase comum.",
                    null
                )
            )
            SKPaymentQueue.Companion.defaultQueue().finishTransaction(transaction) // Finaliza para não travar
        }
    }

    private fun handleFailedTransaction(transaction: SKPaymentTransaction) {
        val error = transaction.error
        val errorMessage = error?.localizedDescription ?: "Erro desconhecido na compra iOS."
        val errorCode = (error?.code ?: -1).toInt()

        if (errorCode == SKErrorCode.SKErrorPaymentCancelled.value.toInt()) {
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.UserCancelled)
            _purchaseUpdates.tryEmit(PurchaseResultModel.UserCancelled)
        } else {
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Error(errorMessage, errorCode))
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error(errorMessage, errorCode))
        }
        SKPaymentQueue.Companion.defaultQueue().finishTransaction(transaction)
    }

    private fun handleRestoredTransaction(transaction: SKPaymentTransaction) {
        val commonPurchase = transaction.toCommonPurchase()
        if (commonPurchase != null) {
            onPurchaseUpdateCallback?.invoke(PurchaseResultModel.Success(commonPurchase)) // Tratado como sucesso para o cliente
            _purchaseUpdates.tryEmit(PurchaseResultModel.Success(commonPurchase))
        }
        SKPaymentQueue.Companion.defaultQueue().finishTransaction(transaction)
    }

    // Extensão para mapear SKPaymentTransaction para nossa classe Purchase
    private fun SKPaymentTransaction.toCommonPurchase(): PurchaseModel? {
        val productId = payment.productIdentifier ?: return null
        val purchaseToken = transactionIdentifier ?: return null // No iOS, transactionIdentifier serve como token
        val purchaseTime =
            transactionDate?.timeIntervalSince1970?.times(1000)?.toLong() ?: 0L // Convert to milliseconds

        return PurchaseModel(
            productId = productId,
            purchaseToken = purchaseToken,
            purchaseTime = purchaseTime,
            orderId = originalTransaction?.transactionIdentifier, // O ID da transação original para restaurações
            quantity = 1, // StoreKit não tem quantidade explícita para um SKPayment
            isAcknowledged = true // iOS não tem um conceito direto de 'acknowledgment' como o Google,
            // o desenvolvedor 'finaliza' a transação quando concede o conteúdo.
        )
    }

    actual fun dispose() {
        SKPaymentQueue.Companion.defaultQueue().removeTransactionObserver(this)
        coroutineScope.cancel() // Cancela as coroutines também
        println("iOS: IapManager descartado. Observador de transações StoreKit 1 removido.")
    }


    override fun productsRequest(
        request: SKProductsRequest,
        didReceiveResponse: SKProductsResponse
    ) {
        // Não implementado, pois já lidamos com isso no método queryProducts
        println("iOS: Produtos recebidos na requisição: ${didReceiveResponse.products}")
    }

    override fun paymentQueue(
        queue: SKPaymentQueue,
        updatedTransactions: List<*>
    ) {
        // Não implementado, mas pode ser usado para lidar com revogação de entitlements
        // Isso pode ser útil para assinaturas ou produtos que podem ser revogados.
        println("iOS: Revogação de entitlements para produtos: $updatedTransactions")
    }

    suspend fun sendReceiptToBackend(base64Receipt: String): Result<String> {
        // Simulação de envio do recibo para o backend
        // Aqui você implementaria a lógica real de envio
        // Exemplo: usando Ktor ou outra biblioteca HTTP
        println("iOS: Enviando recibo para o backend: $base64Receipt")
        val data = NativeRequestService().post("", body = mapOf("receipt" to base64Receipt))
        if (data.status == HttpStatusCode.OK) {
            //println("iOS: Recibo enviado com sucesso.")
            return Result.success(data.body())// Simulação de resposta do backend
        } else {
            println("iOS: Falha ao enviar recibo. Status: ${data.status} - ${data.body<String>()}")
            return Result.failure(Exception("Falha ao enviar recibo: ${data.status} - ${data.body<String>()}"))
        }

        //return data.body()
    }

}
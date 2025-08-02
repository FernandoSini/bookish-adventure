package br.com.flemis.bookishadventure.core.domain.models

// commonMain/kotlin/com/yourapp/iap/Purchase.kt

/**
 * Representa uma compra in-app bem-sucedida.
 */
data class PurchaseModel(
    val productId: String,
    val purchaseToken: String, // Token para validação com o backend do Google/Apple
    val purchaseTime: Long, // Timestamp da compra
    val orderId: String?, // ID da ordem de compra (pode ser nulo)
    val quantity: Int = 1, // Quantidade de itens comprados
    val isAcknowledged: Boolean = false // Indica se a compra foi confirmada pela loja
)
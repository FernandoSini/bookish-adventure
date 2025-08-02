package br.com.flemis.bookishadventure.core.domain.models

// commonMain/kotlin/com/yourapp/iap/ProductDetails.kt

/**
 * Representa os detalhes de um produto in-app.
 */
data class ProductDetailsModel(
    val productId: String,
    val name: String,
    val description: String,
    val price: String, // Preço formatado (ex: "R$19,90", "$9.99")
    val currencyCode: String, // Código da moeda (ex: "BRL", "USD")
    val priceAmountMicros: Long, // Preço em micros-unidades da moeda (para cálculo)
    val type: ProductType // Tipo de produto (consumível, não consumível, assinatura)
)
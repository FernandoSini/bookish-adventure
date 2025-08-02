package br.com.flemis.bookishadventure.core.domain.models

// commonMain/kotlin/com/yourapp/iap/PurchaseResult.kt


/**
 * Representa o resultado de uma operação de compra.
 */
sealed class PurchaseResultModel {
    data class Success(val purchase: PurchaseModel) : PurchaseResultModel()
    object UserCancelled : PurchaseResultModel()
    data class Error(val message: String, val errorCode: Int? = null) : PurchaseResultModel()
    object Pending : PurchaseResultModel() // Compra pendente de confirmação (ex: pagamento em análise)
}

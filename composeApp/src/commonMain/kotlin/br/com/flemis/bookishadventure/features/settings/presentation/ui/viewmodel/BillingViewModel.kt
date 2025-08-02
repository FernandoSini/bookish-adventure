package br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Wallet
import androidx.lifecycle.ViewModel
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.billing_history
import bookishadventure.composeapp.generated.resources.change_billing
import bookishadventure.composeapp.generated.resources.subscription
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

class BillingViewModel : ViewModel(), KoinComponent {

    val menuList: MutableStateFlow<MutableList<MutableMap<String, Any>>>;

    init {
        menuList = MutableStateFlow<MutableList<MutableMap<String, Any>>>(
            mutableListOf(
                mutableMapOf(
                    "screen" to "subscription-details",
                    "icon" to Icons.Default.Info,
                    "label" to Res.string.subscription
                ),
                mutableMapOf(
                    "screen" to "billing-history",
                    "icon" to Icons.Default.Wallet,
                    "label" to Res.string.billing_history
                ),
                mutableMapOf(
                    "screen" to "change-billing",
                    "icon" to Icons.Default.AddCard,
                    "label" to Res.string.change_billing
                ),
            )
        )
    }


    override fun onCleared() {
        super.onCleared()
    }
}
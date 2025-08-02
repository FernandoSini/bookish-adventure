package br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel

class PaywallViewModel : ViewModel() {


    val benefitsList = mutableListOf<String>(
        "Check/create events related this place.",
        "Notify about your travels coming date.",
        "Accurate results about place and flights.",
        "Schedule your travel. ",
        "Chat with travel guide from that place",
        "Customized icons.",
        "Verify wheater in realtime."
    )

    val subscriptionList = mutableListOf<Map<String, String>>(
        mapOf(
            "title" to "Monthly",
            "price" to "$4.99",
            "subtitle" to "Month",
            "discount" to "Month",
            "description" to "Monthly subscription"
        ),
        mapOf(
            "title" to "Yearly",
            "price" to "$4.99",
            "subtitle" to "year",
            "discount" to "Save 35%",
            "description" to "Yearly subscription"
        ),
    )
}
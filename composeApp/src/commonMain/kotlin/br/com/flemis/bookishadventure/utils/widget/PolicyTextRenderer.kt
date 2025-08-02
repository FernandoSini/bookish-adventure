package br.com.flemis.bookishadventure.utils.widget

import androidx.compose.runtime.MutableState
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.billing_policy
import bookishadventure.composeapp.generated.resources.privacy_policy
import bookishadventure.composeapp.generated.resources.terms_of_service
import bookishadventure.composeapp.generated.resources.terms_of_use
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
fun renderTitle(
    scope: CoroutineScope, titleState: MutableState<StringResource>, currentPolicy: String
) {
    scope.launch {
        titleState.value = when (currentPolicy) {
            "privacy_policy" -> Res.string.privacy_policy
            "terms_of_use" -> Res.string.terms_of_use
            "terms_of_service" -> Res.string.terms_of_service
            "billing_policy" -> Res.string.billing_policy
            else -> Res.string.privacy_policy
        }
    }
}


@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
fun renderPrivacyPolicyAssets(
    scope: CoroutineScope, textState: MutableState<String>, currentLanguage: String
) {
    scope.launch {
        try {


            textState.value = when (currentLanguage) {
                "en-US" -> Res.readBytes("files/privacy_policy_en.txt").decodeToString()
                "en_US" -> Res.readBytes("files/privacy_policy_en.txt").decodeToString()
                "en" -> Res.readBytes("files/privacy_policy_en.txt").decodeToString()
                "pt-BR" -> Res.readBytes("files/privacy_policy_pt_br.txt").decodeToString()
                "pt_BR" -> Res.readBytes("files/privacy_policy_pt_br.txt").decodeToString()
                "pt" -> Res.readBytes("files/privacy_policy_pt_br.txt").decodeToString()
                else -> Res.readBytes("files/privacy_policy_en.txt").decodeToString()

            }
        } catch (e: Exception) {
            textState.value = "Error loading policy"
            println("privacy policy error: ${e.message}")
        }
    }
}

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
fun renderTermsOfServiceAssets(
    scope: CoroutineScope, textState: MutableState<String>, currentLanguage: String
) {
    scope.launch {
        try {


            textState.value = when (currentLanguage) {
                "en-US" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                "en_US" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                "en" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                "pt-BR" -> Res.readBytes("files/terms_of_service_pt_br.txt").decodeToString()

                "pt_BR" -> Res.readBytes("files/terms_of_service_pt_br.txt").decodeToString()

                "pt" -> Res.readBytes("files/terms_of_service_pt_br.txt").decodeToString()
                else -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()

            }
        } catch (e: Exception) {
            textState.value = "Error loading policy"
            println("terms of service error: ${e.message}")
        }
    }
}

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
fun renderUseTermsAssets(
    scope: CoroutineScope, textState: MutableState<String>, currentLanguage: String
) {
    scope.launch {
        try {
            textState.value = when (currentLanguage) {
                "en-US" -> Res.readBytes("files/terms_of_use_en.txt").decodeToString()
                "en_US" -> Res.readBytes("files/terms_of_use_en.txt").decodeToString()
                "en" -> Res.readBytes("files/terms_of_use_en.txt").decodeToString()
                "pt-BR" -> Res.readBytes("files/terms_of_use_pt_br.txt").decodeToString()
                "pt_BR" -> Res.readBytes("files/terms_of_use_pt_br.txt").decodeToString()
                "pt" -> Res.readBytes("files/terms_of_use_pt_br.txt").decodeToString()
                else -> Res.readBytes("files/terms_of_use_en.txt").decodeToString()
            }
        } catch (e: Exception) {
            textState.value = "Error loading policy"
            println("terms use error: ${e.message}")
        }
    }
}

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
fun renderBillingPolicyAssets(
    scope: CoroutineScope, textState: MutableState<String>, currentLanguage: String
) {
    scope.launch {
        textState.value = when (currentLanguage) {
            "en-US" -> Res.readBytes("files/billing_policy_en.txt").decodeToString()
            "en_US" -> Res.readBytes("files/billing_policy_en.txt").decodeToString()
            "en" -> Res.readBytes("files/billing_policy_en.txt").decodeToString()
            "pt-BR" -> Res.readBytes("files/billing_policy_pt_br.txt").decodeToString()
            "pt_BR" -> Res.readBytes("files/billing_policy_pt_br.txt").decodeToString()
            "pt" -> Res.readBytes("files/billing_policy_pt_br.txt").decodeToString()
            else -> Res.readBytes("files/billing_policy_en.txt").decodeToString()

        }
    }
}

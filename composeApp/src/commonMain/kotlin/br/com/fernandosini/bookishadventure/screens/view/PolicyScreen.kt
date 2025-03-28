package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.app_name
import bookishadventure.composeapp.generated.resources.billing_policy
import bookishadventure.composeapp.generated.resources.flights
import bookishadventure.composeapp.generated.resources.privacy_policy
import bookishadventure.composeapp.generated.resources.terms_of_service
import bookishadventure.composeapp.generated.resources.terms_of_use
import br.com.fernandosini.bookishadventure.Platform
import br.com.fernandosini.bookishadventure.getPlatform
import br.com.fernandosini.bookishadventure.getPlatformLocale
import br.com.fernandosini.bookishadventure.rememberAppLocale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getResourceUri
import org.jetbrains.compose.resources.readResourceBytes
import org.jetbrains.compose.resources.stringResource

class PolicyScreen(
    private val navController: NavController,
    private val savedStateHandle: SavedStateHandle?
) {
    val localAppLocalization = compositionLocalOf {
        "en-US"
    }
    private val policyType =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("policyType")
            ?: "privacy_policy"

    @OptIn(
        ExperimentalMaterial3Api::class, InternalResourceApi::class,
        ExperimentalResourceApi::class
    )
    @Composable
    fun Content() {

        var textState = remember { mutableStateOf("") }
        var titleState = remember { mutableStateOf<StringResource>(Res.string.app_name) }
        val scrollState = rememberScrollState()
        val currentLocale = rememberAppLocale()

        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            //textState = Res.readBytes("files/privacy_policy_en.txt").decodeToString()
            when (policyType) {
                "privacy_policy" -> renderPrivacyPolicyAssets(scope, textState = textState, currentLocale)
                "terms_of_use" -> renderUseTermsAssets(scope, textState = textState, currentLocale)
                "billing_policy" -> renderBillingPolicyAssets(
                    scope,
                    textState = textState,
                    currentLocale
                )

                "terms_of_service" -> renderTermsOfServiceAssets(
                    scope,
                    textState = textState,
                    currentLocale
                )

                else -> renderPrivacyPolicyAssets(scope, textState = textState, currentLocale)
            }
            when (policyType) {
                "privacy_policy" -> renderTitle(
                    scope,
                    titleState = titleState,
                    currentPolicy = policyType
                )

                "terms_of_use" -> renderTitle(
                    scope,
                    titleState = titleState,
                    currentPolicy = policyType
                )

                "billing_policy" -> renderTitle(
                    scope,
                    titleState = titleState,
                    currentPolicy = policyType
                )

                else -> renderTitle(scope, titleState = titleState, currentPolicy = policyType)
            }

        }

        CompositionLocalProvider(localAppLocalization provides currentLocale) {
            Scaffold(
                modifier = Modifier.navigationBarsPadding().fillMaxSize(),
                contentWindowInsets = WindowInsets.systemBars,
                backgroundColor = Color.Black,
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        title = {
                            if (getPlatform().name.lowercase().contains("ios")) {
                                Text(
                                    stringResource(titleState.value), style = TextStyle(
                                        color = Color.White,
                                        fontFamily = FontFamily(
                                            Font(Res.font.DMSans_SemiBold)
                                        ),
                                        fontSize = 20.sp,
                                    ),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            } else {
                                null
                            }


                        },
                        modifier = Modifier,
                        navigationIcon = {
                            if (getPlatform().name.lowercase().contains("android")) {
                                Row() {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            tint = Color.White,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Text(
                                        stringResource(titleState.value), style = TextStyle(
                                            color = Color.White,
                                            fontFamily = FontFamily(
                                                Font(Res.font.DMSans_SemiBold)
                                            ),
                                            fontSize = 20.sp,
                                        ),
                                        modifier = Modifier.padding(start = 10.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            } else {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                                        tint = Color.White,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                        }
                    )
                }) {
                Column(
                    Modifier.padding(bottom = it.calculateBottomPadding()).wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) { }
                Text(
                    textState.value,
                    modifier = Modifier
                        .wrapContentHeight().verticalScroll(scrollState, enabled = true)
                        .padding(bottom = 30.dp, end = 10.dp),
                    textAlign = TextAlign.Justify,
                    style = TextStyle(textIndent = TextIndent(firstLine = 10.sp, restLine = 10.sp)),
                    color = Color.White,
                    softWrap = true
                )
                //   Text("$textState", color = Color.White)
            }

        }
    }

    @OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)

    fun renderTitle(
        scope: CoroutineScope,
        titleState: MutableState<StringResource>,
        currentPolicy: String
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
        scope: CoroutineScope,
        textState: MutableState<String>,
        currentLanguage: String
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
            scope: CoroutineScope,
            textState: MutableState<String>,
            currentLanguage: String
        ) {
            scope.launch {
                try {


                    textState.value = when (currentLanguage) {
                        "en-US" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                        "en_US" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                        "en" -> Res.readBytes("files/terms_of_service_en.txt").decodeToString()
                        "pt-BR" -> Res.readBytes("files/terms_of_service_pt_br.txt")
                            .decodeToString()

                        "pt_BR" -> Res.readBytes("files/terms_of_service_pt_br.txt")
                            .decodeToString()

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
            scope: CoroutineScope,
            textState: MutableState<String>,
            currentLanguage: String
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
            scope: CoroutineScope,
            textState: MutableState<String>,
            currentLanguage: String
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


}

package br.com.flemis.bookishadventure.presentation.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.rememberAppLocale
import br.com.flemis.bookishadventure.utils.widget.renderBillingPolicyAssets
import br.com.flemis.bookishadventure.utils.widget.renderPrivacyPolicyAssets
import br.com.flemis.bookishadventure.utils.widget.renderTermsOfServiceAssets
import br.com.flemis.bookishadventure.utils.widget.renderTitle
import br.com.flemis.bookishadventure.utils.widget.renderUseTermsAssets
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class PolicyScreen(
    private val navController: NavController, private val savedStateHandle: SavedStateHandle?
) {
    val localAppLocalization = compositionLocalOf { "en-US" }
    private val policyType =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("policyType") ?: "privacy_policy"

    @OptIn(ExperimentalMaterial3Api::class, InternalResourceApi::class, ExperimentalResourceApi::class)
    @Composable
    fun Content() {
        var textState = remember { mutableStateOf("") }
        var titleState = remember { mutableStateOf<StringResource>(Res.string.app_name) }
        val scrollState = rememberScrollState()
        val currentLocale = rememberAppLocale()
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            when (policyType) {
                "privacy_policy" -> renderPrivacyPolicyAssets(scope, textState = textState, currentLocale)
                "terms_of_use" -> renderUseTermsAssets(scope, textState = textState, currentLocale)
                "billing_policy" -> renderBillingPolicyAssets(scope, textState = textState, currentLocale)
                "terms_of_service" -> renderTermsOfServiceAssets(scope, textState = textState, currentLocale)
                else -> renderPrivacyPolicyAssets(scope, textState = textState, currentLocale)
            }
            when (policyType) {
                "privacy_policy" -> renderTitle(scope, titleState = titleState, currentPolicy = policyType)
                "terms_of_use" -> renderTitle(scope, titleState = titleState, currentPolicy = policyType)
                "billing_policy" -> renderTitle(scope, titleState = titleState, currentPolicy = policyType)
                else -> renderTitle(scope, titleState = titleState, currentPolicy = policyType)
            }

        }

        CompositionLocalProvider(localAppLocalization provides currentLocale) {
            Scaffold(
                modifier = Modifier.navigationBarsPadding().fillMaxSize(),
                contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
                backgroundColor = MaterialTheme.colorScheme.background,
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier,
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                        title = {
                            if (getPlatform().name.lowercase().contains("ios")) {
                                Text(
                                    stringResource(titleState.value),
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            } else {
                                null
                            }
                        },
                        navigationIcon = {
                            if (getPlatform().name.lowercase().contains("android")) {
                                Row(content = {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            tint = MaterialTheme.colorScheme.surfaceTint,
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
                                        ), modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)
                                    )
                                })
                            } else {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                                        tint = MaterialTheme.colorScheme.surfaceTint,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        })
                },
                content = {
                    Text(
                        textState.value,
                        modifier = Modifier.wrapContentHeight().verticalScroll(scrollState, enabled = true)
                            .padding(bottom = 30.dp, end = 10.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textIndent = TextIndent(firstLine = 10.sp, restLine = 10.sp),
                            textAlign = TextAlign.Justify,
                        ),
                        softWrap = true
                    )
                })

        }
    }



}

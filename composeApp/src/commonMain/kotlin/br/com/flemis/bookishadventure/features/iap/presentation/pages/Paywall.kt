package br.com.flemis.bookishadventure.features.iap.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.billing_policy
import bookishadventure.composeapp.generated.resources.cancel_anytime
import bookishadventure.composeapp.generated.resources.continue_text
import bookishadventure.composeapp.generated.resources.paywall_image
import br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.InAppPurchaseViewModel
import br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.PaywallViewModel
import br.com.flemis.bookishadventure.utils.widget.CustomElevatedButton
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class Paywall(private var navController: NavController) {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val iapViewModel = koinViewModel<InAppPurchaseViewModel>()
        val lifecycleOwner = LocalLifecycleOwner.current
        LifecycleEventEffect(Lifecycle.Event.ON_START, lifecycleOwner = lifecycleOwner) {
            iapViewModel.purchaseState.value
        }
        val paywallViewModel = viewModel<PaywallViewModel> { PaywallViewModel() }
        val scrollState = rememberScrollState()
        var selected by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.Companion.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { null },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Companion.Transparent),
                    actions = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.Companion.wrapContentSize().height(40.dp).width(40.dp)
                                .padding(end = 10.dp).background(
                                    MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.3f),
                                    shape = CircleShape
                                ).testTag("CloseIconButton")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon",
                                tint = MaterialTheme.colorScheme.surfaceTint,
                            )
                        }
                    }
                )
            },
            content = {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize().padding(bottom = it.calculateBottomPadding()),
                    color = Color.Companion.Transparent,
                    content = {
                        Column(
                            Modifier.Companion.fillMaxSize().verticalScroll(
                                scrollState,
                                enabled = true,
                                flingBehavior = ScrollableDefaults.flingBehavior()
                            ),
                            content = {
                                BoxWithConstraints(
                                    modifier = Modifier.Companion.height(250.dp).fillMaxWidth(),
                                    content = {
                                        Image(
                                            painterResource(Res.drawable.paywall_image),
                                            modifier = Modifier.Companion.matchParentSize(),
                                            contentScale = ContentScale.Companion.None,
                                            contentDescription = "Paywall Image",
                                        )
                                        Box(
                                            Modifier.Companion.fillMaxSize().background(
                                                brush = Brush.Companion.verticalGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.background.copy(alpha = 1f),
                                                        //Color.Yellow,
                                                        Color.Companion.Transparent
                                                    ),
                                                    endY = 0f,
                                                    startY = constraints.maxHeight.toFloat(),
                                                )
                                            ),
                                            content = {
                                                Column(
                                                    modifier = Modifier.Companion.matchParentSize()
                                                        .padding(horizontal = 25.dp)
                                                        .fillMaxSize(),

                                                    verticalArrangement = Arrangement.Bottom,
                                                    horizontalAlignment = Alignment.Companion.Start,
                                                    content = {
                                                        Text(
                                                            "Unlock feature",
                                                            style = MaterialTheme.typography.headlineSmall
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
                                )
                                BoxWithConstraints(
                                    modifier = Modifier.Companion.fillMaxHeight(0.25f).fillMaxWidth()
                                        .padding(top = 20.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.Companion,
                                        verticalArrangement = Arrangement.spacedBy(20.dp),
                                        content = {
                                            paywallViewModel.benefitsList.map {
                                                Row(
                                                    modifier = Modifier.Companion.fillMaxWidth()
                                                        .padding(horizontal = 25.dp)

                                                ) {
                                                    Icon(
                                                        Icons.Default.Verified,
                                                        contentDescription = "Verified Icon",
                                                        tint = MaterialTheme.colorScheme.surfaceTint,
                                                        modifier = Modifier.Companion.size(20.dp),
                                                    )
                                                    Text(
                                                        it,
                                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                                        modifier = Modifier.Companion.padding(start = 10.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.Companion.weight(1f))
                                            paywallViewModel.subscriptionList.mapIndexed { index, it ->
                                                //depois verificar porque esta errado isso aqui
                                                Row(
                                                    modifier = Modifier.Companion.fillMaxWidth()
                                                        .padding(horizontal = 25.dp)
                                                        .clickable(
                                                            indication = null,
                                                            onClick = {
                                                                selected = it
                                                            },
                                                            interactionSource = remember { MutableInteractionSource() }),
                                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                                    verticalAlignment = Alignment.Companion.CenterVertically,
                                                    content = {
                                                        Column() {
                                                            Row(
                                                                verticalAlignment = Alignment.Companion.Bottom,
                                                                horizontalArrangement = Arrangement.Start,
                                                                modifier = Modifier.Companion.fillMaxWidth(0.7f)

                                                            ) {
                                                                Text(
                                                                    it["title"].toString(),
                                                                    style = MaterialTheme.typography.titleMedium.copy(
                                                                        fontSize = 18.sp,
                                                                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                                                                    )
                                                                )
                                                                Spacer(modifier = Modifier.Companion.width(10.dp))
                                                                Text(
                                                                    it["price"].toString() + "/" + it["subtitle"],
                                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                                        fontFamily = FontFamily(Font(Res.font.DMSans_Regular))
                                                                    ),
                                                                    modifier = Modifier.Companion
                                                                )
                                                            }
                                                            Row(
                                                                modifier = Modifier.Companion.fillMaxWidth(0.7f)
                                                                    .padding(top = 5.dp),
                                                                horizontalArrangement = Arrangement.Start,
                                                                content = {
                                                                    Text(
                                                                        it["discount"].toString(),
                                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                                            fontFamily = FontFamily(Font(Res.font.DMSans_Regular))
                                                                        ),
                                                                        modifier = Modifier.Companion
                                                                    )
                                                                }
                                                            )
                                                        }
                                                        Spacer(Modifier.Companion.weight(1f))
                                                        Box(
                                                            Modifier.Companion.fillMaxHeight(),
                                                            contentAlignment = Alignment.Companion.Center
                                                        ) {
                                                            if (it == selected) {
                                                                Icon(
                                                                    Icons.Default.CheckCircle,
                                                                    contentDescription = "Check Icon",
                                                                    tint = MaterialTheme.colorScheme.surfaceTint,
                                                                    modifier = Modifier.Companion.size(20.dp),
                                                                )
                                                            } else {
                                                                Box(
                                                                    modifier = Modifier.Companion.size(20.dp).border(
                                                                        1.dp,
                                                                        MaterialTheme.colorScheme.surfaceTint,
                                                                        shape = CircleShape
                                                                    ),
                                                                )

                                                            }
                                                        }
                                                    }
                                                )
                                            }
                                            Column(
                                                modifier = Modifier.Companion.fillMaxWidth()
                                                    .padding(horizontal = 25.dp),
                                                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                                content = {
                                                    CustomElevatedButton(
                                                        placeholder = stringResource(Res.string.continue_text),
                                                        onClick = {},
                                                    )
                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.Companion.CenterVertically,
                                                        modifier = Modifier.Companion.fillMaxWidth(),
                                                        content = {
                                                            Text(
                                                                stringResource(Res.string.cancel_anytime),
                                                                modifier = Modifier.Companion,
                                                                style = MaterialTheme.typography.bodySmall.copy(
                                                                    color = MaterialTheme.colorScheme.inversePrimary,
                                                                    textAlign = TextAlign.Companion.End,
                                                                )
                                                            )
                                                            TextButton(
                                                                contentPadding = PaddingValues(start = 5.dp),
                                                                onClick = {
                                                                    navController.let { it ->
                                                                        it.currentBackStackEntry?.savedStateHandle?.set(
                                                                            "policyType", "billing_policy"
                                                                        )
                                                                        it.navigate("policy")
                                                                    }
                                                                },
                                                            ) {
                                                                Text(
                                                                    stringResource(Res.string.billing_policy),
                                                                    modifier = Modifier.Companion,
                                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                                        fontSize = 13.sp,
                                                                        textAlign = TextAlign.Companion.Start,
                                                                        color = Color(0xff0B85F7),
                                                                        textDecoration = TextDecoration.Companion.Underline
                                                                    )

                                                                )
                                                            }
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    }
                )
            }
        )
    }
}
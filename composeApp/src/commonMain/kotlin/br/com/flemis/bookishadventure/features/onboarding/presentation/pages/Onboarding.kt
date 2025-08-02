package br.com.flemis.bookishadventure.features.onboarding.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.happy
import bookishadventure.composeapp.generated.resources.onboarding
import bookishadventure.composeapp.generated.resources.planning_travel
import br.com.flemis.bookishadventure.features.onboarding.presentation.ui.viewmodel.PermissionViewModel
import br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel.SplashViewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

class Onboarding(private var navController: NavController) {
    val listOnboardingItems = listOf(
        mutableMapOf(

            "image" to Res.drawable.happy,
            "description" to "We are happy to see you starting using our services",

            ), mutableMapOf(
            "image" to Res.drawable.planning_travel,
            "description" to "We are a platform that helps you to find the best places/recomendation of places for you while you travel.",
        ), mutableMapOf(
            "image" to Res.drawable.onboarding,
            "description" to "So to get a complete experience,\n please accept the permissions to get onboard."
        )
    )

    @Composable
    fun Content() {
        val permissionList = listOf("tracking","camera", "gallery", "storage", "location", "microphone", "calendar")

        val snackbarHostState = remember { SnackbarHostState() }
        val permissionController = rememberPermissionsControllerFactory().createPermissionsController()
        val pagerState = rememberPagerState(pageCount = { listOnboardingItems.size })
        val scrollState = rememberScrollState()
        val permissionViewModel = viewModel<PermissionViewModel> {
            PermissionViewModel()
        }
        val splashViewModel = viewModel<SplashViewModel> { SplashViewModel() }
        val scope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        BindEffect(permissionController)
        LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME, lifecycleOwner = lifecycleOwner) {
            scope.launch {
                if (permissionViewModel.isAllPermissionsGranted.value) {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }
        }
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets.Companion.safeContent,
            modifier = Modifier.Companion.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier.Companion.navigationBarsPadding()
                            .wrapContentSize(Alignment.Companion.BottomCenter),
                        snackbarData = data,
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.typography.labelSmall.color,
                        actionColor = MaterialTheme.colorScheme.surfaceTint,
                    )
                }
            },
            content = {
                Surface(
                    elevation = 0.dp,
                    color = Color.Companion.Transparent,
                    modifier = Modifier.Companion.fillMaxSize().padding(top = it.calculateTopPadding()),
                    content = {
                        Column(
                            modifier = Modifier.Companion.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.Companion.CenterHorizontally,
                            content = {
                                BoxWithConstraints(
                                    modifier = Modifier.Companion.fillMaxWidth().padding(horizontal = 20.dp),
                                    content = {
                                        Text(
                                            "Welcome to Bookish Adventure.",
                                            textAlign = TextAlign.Companion.Center,
                                            softWrap = true,
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontFamily = FontFamily(
                                                    Font(Res.font.DMSans_Bold)
                                                ),
                                            )
                                        )
                                    }
                                )
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier.Companion.wrapContentSize(Alignment.Companion.Center),
                                    pageContent = { page ->
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(30.dp),
                                            horizontalAlignment = Alignment.Companion.CenterHorizontally,
                                            content = {
                                                BoxWithConstraints(
                                                    modifier = Modifier.Companion.wrapContentSize(Alignment.Companion.Center)
                                                        .size(350.dp),
                                                    content = {
                                                        Image(
                                                            painter = painterResource(listOnboardingItems[page]["image"] as DrawableResource),
                                                            contentDescription = null,
                                                            contentScale = ContentScale.Companion.Fit,
                                                            modifier = Modifier.Companion.fillMaxSize()
                                                        )
                                                    }
                                                )
                                                BoxWithConstraints(
                                                    modifier = Modifier.Companion.fillMaxWidth().height(100.dp)
                                                        .padding(horizontal = 20.dp),
                                                    content = {
                                                        Text(
                                                            text = listOnboardingItems[page]["description"] as String,
                                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                                fontSize = 18.sp,
                                                                textAlign = TextAlign.Companion.Center
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
                                )
                                Row(
                                    Modifier.Companion.wrapContentHeight().fillMaxWidth()
                                        .align(Alignment.Companion.CenterHorizontally).padding(bottom = 8.dp)
                                        .height(50.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Companion.CenterVertically,
                                    content = {
                                        if (pagerState.currentPage == 2) {
                                            TextButton(
                                                onClick = {
                                                    scope.launch {
                                                        permissionViewModel.requestPermissions(
                                                            permissionList,
                                                            snackbarHostState
                                                        )
                                                        splashViewModel.setFirstTimeInApp(false)
                                                    }
                                                },
                                                content = {
                                                    Text(
                                                        text = "Accept",
                                                        style = MaterialTheme.typography.titleMedium.copy(
                                                            fontSize = 18.sp, textAlign = TextAlign.Companion.Center
                                                        )
                                                    )
                                                }
                                            )
                                        } else {
                                            repeat(pagerState.pageCount) { iteration ->
                                                val color =
                                                    if (pagerState.currentPage == iteration)
                                                        MaterialTheme.colorScheme.surfaceTint
                                                    else Color.Companion.LightGray
                                                Box(
                                                    modifier = Modifier.Companion.padding(2.dp).clip(CircleShape)
                                                        .background(color).size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}
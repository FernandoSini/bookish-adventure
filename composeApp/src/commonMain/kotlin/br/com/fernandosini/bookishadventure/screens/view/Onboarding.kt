package br.com.fernandosini.bookishadventure.screens.view

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
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
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.happy
import bookishadventure.composeapp.generated.resources.onboarding
import bookishadventure.composeapp.generated.resources.planning_travel
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.viewmodel.PermissionViewModel
import br.com.fernandosini.bookishadventure.screens.viewmodel.SplashViewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

class Onboarding(private val appDatabase: AppDatabase, private var navController: NavController) {
    val listOnboardingItems = listOf(
        mutableMapOf
            (

            "image" to Res.drawable.happy,
            "description" to "We are happy to see you starting using our services",

            ),
        mutableMapOf(
            "image" to Res.drawable.planning_travel,
            "description" to "We are a platform that helps you to find the best places/recomendation of places for you while you travel.",
        ),
        mutableMapOf(
            "image" to Res.drawable.onboarding,
            "description" to "So to get a complete experience,\n please accept the permissions to get onboard."
        )
    )

    @Composable
    fun Content() {
        val permissionList = listOf(
            Permission.CAMERA,
            Permission.GALLERY,
            Permission.STORAGE,
            Permission.WRITE_STORAGE,
            Permission.LOCATION,
            Permission.COARSE_LOCATION,
            Permission.BACKGROUND_LOCATION,
            Permission.RECORD_AUDIO,
            /* Permission.REMOTE_NOTIFICATION,*/
        )
        val snackbarHostState = remember { SnackbarHostState() }
        val permissionController =
            rememberPermissionsControllerFactory().createPermissionsController()
        val pagerState = rememberPagerState(pageCount = { listOnboardingItems.size })
        val scrollState = rememberScrollState()
        val permissionViewModel = viewModel<PermissionViewModel> {
            PermissionViewModel(permissionController)
        }
        val splashViewModel = viewModel<SplashViewModel> { SplashViewModel() }
        val scope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        BindEffect(permissionController)

        LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME, lifecycleOwner = lifecycleOwner) {
            scope.launch {
                println("fefe"+permissionViewModel.isAllPermissionsGranted.value)
                if (permissionViewModel.isAllPermissionsGranted.value) {
                    navController.navigate("login") {

                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }
        }

        Scaffold(
            containerColor = Color.Black,
            contentWindowInsets = WindowInsets.safeContent,
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) }

            ) {
            Surface(
                elevation = 0.dp,
                color = Color.Transparent,
                modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    ) {
                        Text(
                            "Welcome to Bookish Adventure.",
                            textAlign = TextAlign.Center,
                            softWrap = true, style = TextStyle(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(Res.font.DMSans_Bold)
                                ),
                                fontSize = 24.sp
                            )
                        )
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.wrapContentSize(Alignment.Center)
                    ) { page ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BoxWithConstraints(

                                modifier = Modifier.wrapContentSize(Alignment.Center).size(350.dp),
                            ) {
                                Image(
                                    painter = painterResource(listOnboardingItems[page]["image"] as DrawableResource),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxWidth().height(100.dp)
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = listOnboardingItems[page]["description"] as String,
                                    style = TextStyle(
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(Res.font.DMSans_Regular)),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center
                                    )

                                )
                            }
                        }
                    }


                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 8.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (pagerState.currentPage == 2) {


                            TextButton(onClick = {
                                scope.launch {
                                    permissionViewModel.requestPermissions(
                                        permissionList,
                                        snackbarHostState
                                    )
                                    splashViewModel.setFirstTimeInApp(false)

                                }

                            }) {
                                Text(
                                    text = "Accept",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        } else {
                            repeat(pagerState.pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.White else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
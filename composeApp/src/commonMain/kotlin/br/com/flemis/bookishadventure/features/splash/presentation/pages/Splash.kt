package br.com.flemis.bookishadventure.features.splash.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.background_travel
import bookishadventure.composeapp.generated.resources.plane_filled
import br.com.flemis.bookishadventure.features.splash.presentation.ui.viewmodel.SplashViewModel
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.features.onboarding.presentation.ui.viewmodel.PermissionViewModel
import br.com.flemis.bookishadventure.rememberAppLocale
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.gallery.GALLERY
import dev.icerock.moko.permissions.location.BACKGROUND_LOCATION
import dev.icerock.moko.permissions.location.COARSE_LOCATION
import dev.icerock.moko.permissions.location.LOCATION
import dev.icerock.moko.permissions.microphone.RECORD_AUDIO
import dev.icerock.moko.permissions.storage.STORAGE
import dev.icerock.moko.permissions.storage.WRITE_STORAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.seconds

class Splash(private var navController: NavController) {

    val localAppLocalization = compositionLocalOf {
        "en-US"
    }

    @Composable
    fun Content() {
        val currentLanguage = rememberAppLocale()
        val permissionList = listOf(
            Permission.Companion.CAMERA,
            Permission.Companion.GALLERY,
            Permission.Companion.STORAGE,
            Permission.Companion.WRITE_STORAGE,
            Permission.Companion.LOCATION,
            Permission.Companion.COARSE_LOCATION,
            Permission.Companion.BACKGROUND_LOCATION,
            Permission.Companion.RECORD_AUDIO,
            /* Permission.REMOTE_NOTIFICATION,*/
        )
        val permissionsController =
            rememberPermissionsControllerFactory().createPermissionsController()
        val snackbarHostState = remember { SnackbarHostState() }
        val permissionViewModel =
            viewModel<PermissionViewModel> { PermissionViewModel() }
        val lifecycleOwner = LocalLifecycleOwner.current
        val scope = rememberCoroutineScope()
        val splashViewModel = viewModel<SplashViewModel>() { SplashViewModel() }
        val state by splashViewModel.state.collectAsState()


        LifecycleEventEffect(event = Lifecycle.Event.ON_START, lifecycleOwner = lifecycleOwner) {
            scope.launch {
                if (state.isFirstTime) {
                    delay(5.seconds)
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    if (permissionViewModel.isAllPermissionsGranted.value) {
                        state.isLoading = true
                        delay(5.seconds)
                        navController.navigate("base") {
                            popUpTo("splash") { inclusive = true }
                        }
                        state.isLoading
                    } else {
                        state.isLoading = true
                        delay(5.seconds)
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                        state.isLoading = false
                    }
                }
            }
        }

        CompositionLocalProvider(localAppLocalization provides currentLanguage) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                modifier = Modifier.Companion.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
                contentWindowInsets = WindowInsets.Companion.systemBars,
                content = {
                    Box(
                        modifier = Modifier.Companion.fillMaxSize(),
                    ) {
                        Image(
                            painterResource(Res.drawable.background_travel),
                            contentDescription = null,
                            modifier = Modifier.Companion.fillMaxSize(),
                            contentScale = ContentScale.Companion.FillHeight,
                            colorFilter = ColorFilter.Companion.tint(Color(0xffC6E2FF), BlendMode.Companion.Darken)
                        )
                        Column(
                            modifier = Modifier.Companion.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.Companion.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.Companion)
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                                modifier = Modifier.Companion.fillMaxWidth().height(300.dp)
                            ) {
                                Row(
                                    modifier = Modifier.Companion.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Companion.CenterVertically
                                ) {
                                    Image(
                                        painterResource(Res.drawable.plane_filled),

                                        contentDescription = null,
                                        colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
                                        modifier = Modifier.Companion.height(150.dp).width(150.dp)
                                            .padding(bottom = 20.dp)
                                            .rotate(320f)
                                    )
                                    Text(
                                        "Bookish\nAdventure",
                                        color = Color.Companion.White,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Companion.W600,
                                            fontFamily = FontFamily(
                                                Font(Res.font.DMSans_Bold)
                                            )
                                        ),
                                    )
                                }
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.Companion.White,
                                        strokeWidth = 5.dp
                                    )
                                }
                                Spacer(Modifier.Companion.padding(vertical = 5.dp))
                                Text(
                                    "v${getPlatform().appVersion}",
                                    color = Color.Companion.White,
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold))
                                    )
                                )
                            }
                            Spacer(Modifier.Companion)
                        }
                    }
                }
            )


        }
    }
}
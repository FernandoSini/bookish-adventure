package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navArgument
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.background_travel
import bookishadventure.composeapp.generated.resources.plane_filled
import br.com.fernandosini.bookishadventure.getPlatform
import br.com.fernandosini.bookishadventure.rememberAppLocale
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.ViewModel.PermissionViewModel
import br.com.fernandosini.bookishadventure.screens.ViewModel.SplashViewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.seconds

class Splash(private val appDatabase: AppDatabase, private var navController: NavController) {

    val localAppLocalization = compositionLocalOf {
        "en-US"
    }

    @Composable
    fun Content() {
        val currentLanguage = rememberAppLocale()
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
        val permissionsController =
            rememberPermissionsControllerFactory().createPermissionsController()
        val snackbarHostState = remember { SnackbarHostState() }
        val permissionViewModel =
            viewModel<PermissionViewModel> { PermissionViewModel(permissionsController) }
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
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                    state.isLoading
                    } else {
                        state.isLoading = true
                        delay(5.seconds)
                        navController.navigate("base") {
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
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Black,
                contentWindowInsets = WindowInsets.systemBars,

                ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Image(
                        painterResource(Res.drawable.background_travel),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillHeight,
                        colorFilter = ColorFilter.tint(Color(0xffC6E2FF), BlendMode.Darken)

                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Spacer(modifier = Modifier)
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(Res.drawable.plane_filled),

                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier.height(150.dp).width(150.dp)
                                        .padding(bottom = 20.dp)
                                        .rotate(320f)
                                )

                                Text(
                                    "Bookish\nAdventure",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W600,
                                        fontFamily = FontFamily(
                                            Font(Res.font.DMSans_Bold)
                                        )
                                    ),
                                )
                            }


                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 5.dp
                                )
                            }
                            Spacer(Modifier.padding(vertical = 5.dp))
                            Text(
                                "v${getPlatform().appVersion}",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold))
                                )

                            )

                        }
                        Spacer(Modifier)


                    }

                }
            }
        }
    }
}
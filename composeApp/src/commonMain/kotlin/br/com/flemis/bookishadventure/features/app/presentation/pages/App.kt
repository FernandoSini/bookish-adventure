package br.com.flemis.bookishadventure.features.app.presentation.pages

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.flemis.bookishadventure.features.settings.data.repository.implementations.ThemeRepositoryImpl
import br.com.flemis.bookishadventure.features.base.presentation.pages.Base
import br.com.flemis.bookishadventure.features.auth.presentation.pages.Login
import br.com.flemis.bookishadventure.features.onboarding.presentation.pages.Onboarding
import br.com.flemis.bookishadventure.presentation.pages.PolicyScreen
import br.com.flemis.bookishadventure.features.auth.presentation.pages.SignUp
import br.com.flemis.bookishadventure.features.splash.presentation.pages.Splash
import br.com.flemis.bookishadventure.features.auth.presentation.pages.VerifyCodeScreen
import br.com.flemis.bookishadventure.features.iap.presentation.ui.viewmodel.InAppPurchaseViewModel
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.presentation.viewmodel.UserViewModel
import br.com.flemis.bookishadventure.utils.themes.MyCustomTheme

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

//import org.koin.compose.viewmodel.koinViewModel
//import org.koin.core.annotation.KoinExperimentalAPI

/*
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(Res.drawable.plane_filled),
                        null,
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(Color.Magenta)
                    )
                    Text("Compose: $greeting")
                }
            }
        }
    }
}*/

//@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    /*val inAppPurchaseViewModel = viewModel<InAppPurchaseViewModel> {
        val inAppPurchaseUseCase = InAppPurchaseUseCase(iapManager)
        InAppPurchaseViewModel(inAppPurchaseUseCase)
    }*/
    val inAppPurchaseViewModel = koinViewModel<InAppPurchaseViewModel>()
   // inAppPurchaseViewModel.onCleared()
    val navigator = rememberNavController()
    val themeRepository = ThemeRepositoryImpl()
   // val settings: Settings = Settings()
   /* val themeViewModel = viewModel<ThemeViewModel> {
        val themeUseCase = ThemeUseCase(themeRepository)
        ThemeViewModel(
            themeUseCase = themeUseCase,
            settings = settings
        )
    }*/
    val themeViewModel = koinViewModel<ThemeViewModel>()
    val darkModeState by themeViewModel.state.collectAsState()
  /*  val userService = UserServiceImpl()
    val localDataSource: LocalDataSource = LocalDataSourceImpl()
    val authService = AuthServiceImpl()
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(userService = userService, authService = authService)
    val userRepository: UserRepositoryImpl = UserRepositoryImpl(localDataSource, remoteDataSource)
    val userViewModel: UserViewModel = viewModel<UserViewModel> { UserViewModel() }*/

    val userViewModel= koinViewModel<UserViewModel>()


    MyCustomTheme(isDarkTheme = darkModeState.isDarkMode) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets.safeContent,
            modifier = Modifier.testTag("initial_scaffold")
        ) { innerPadding ->
            NavHost(
                navController = navigator,
                startDestination = "splash",
                modifier = Modifier,
                enterTransition = {
                    //EnterTransition.None
                    // slideInHorizontally(tween(700))
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                    )
                },
                exitTransition = {
                    //ExitTransition.None
                    // slideOutHorizontally(tween(700))
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                    )
                },
                popEnterTransition = { //EnterTransition.None
                    slideInHorizontally(tween(700))/*  slideIntoContainer(
                      AnimatedContentTransitionScope.SlideDirection.End,
                      tween(700)
                  )*/
                },
                popExitTransition = {
                    //  slideOutHorizontally(tween(700))
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                    )
                },
            ) {

                composable("splash") {
                    Splash( navigator).Content()
                }
                composable("onboarding") {
                    Onboarding(navigator).Content()
                }
                composable("base") {
                    Base().Content(themeViewModel)
                }
                composable("login") {
                    Login( navigator).Content()
                }
                composable("signup") {
                    SignUp( navigator).Content()
                }
                composable("verify-code") {
                    VerifyCodeScreen( navigator).Content()
                }
                composable(
                    //"policy/{policyType}",
                    "policy"
                    //  arguments = listOf(navArgument("policyType") { type = NavType.StringType })
                ) {
                    // val policyType = it.arguments?.getString("policyType") ?: "privacy_policy"
                    PolicyScreen(navController = navigator, it.savedStateHandle).Content()
                }

            }
        }

    }
}





package br.com.fernandosini.bookishadventure.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.view.Base
import br.com.fernandosini.bookishadventure.screens.view.Login
import br.com.fernandosini.bookishadventure.screens.view.Onboarding
import br.com.fernandosini.bookishadventure.screens.view.PolicyScreen
import br.com.fernandosini.bookishadventure.screens.view.SignUp
import br.com.fernandosini.bookishadventure.screens.view.Splash
import br.com.fernandosini.bookishadventure.screens.view.VerifyCodeScreen
import br.com.fernandosini.bookishadventure.screens.viewmodel.ThemeViewModel
import br.com.fernandosini.bookishadventure.utils.themes.MyCustomTheme

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

@Composable
@Preview
fun App(appDatabase: AppDatabase) {

    val navigator = rememberNavController()
    val themeViewModel = viewModel<ThemeViewModel>() { ThemeViewModel() }
    val darkModeState by themeViewModel.state.collectAsState()


    MyCustomTheme(isDarkTheme = darkModeState.isDarkMode) {
        Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
            NavHost(
                navController = navigator,
                startDestination = "splash",
                modifier = Modifier,
                enterTransition = {
                    //EnterTransition.None
                    // slideInHorizontally(tween(700))
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(700)
                    )
                },
                exitTransition = {
                    //ExitTransition.None
                    // slideOutHorizontally(tween(700))
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(700)
                    )
                },
                popEnterTransition = { //EnterTransition.None
                    slideInHorizontally(tween(700))
                    /*  slideIntoContainer(
                      AnimatedContentTransitionScope.SlideDirection.End,
                      tween(700)
                  )*/
                },
                popExitTransition = {
                    //  slideOutHorizontally(tween(700))
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(700)
                    )
                },
            ) {

                composable("splash") {
                    Splash(appDatabase, navigator).Content()
                }
                composable("onboarding") {
                    Onboarding(appDatabase, navigator).Content()
                }
                composable("base") {
                    Base(appDatabase).Content(themeViewModel)
                }
                composable("login") {
                    Login(appDatabase, navigator).Content()
                }
                composable("signup") {
                    SignUp(appDatabase, navigator).Content()
                }
                composable("verify-code") {
                    VerifyCodeScreen(appDatabase, navigator).Content()
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





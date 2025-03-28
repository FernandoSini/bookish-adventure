package br.com.fernandosini.bookishadventure.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fernandosini.bookishadventure.models.Address
import org.jetbrains.compose.ui.tooling.preview.Preview

import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.view.AccountScreen
import br.com.fernandosini.bookishadventure.screens.view.Base
import br.com.fernandosini.bookishadventure.screens.view.Home
import br.com.fernandosini.bookishadventure.screens.view.Login
import br.com.fernandosini.bookishadventure.screens.view.Onboarding
import br.com.fernandosini.bookishadventure.screens.view.SearchScreen
import br.com.fernandosini.bookishadventure.screens.view.SignUp
import br.com.fernandosini.bookishadventure.screens.view.Splash

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
         slideOutHorizontally(tween(700))
            /*slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )*/
        },
    ) {

        composable("splash") {
            Splash(appDatabase, navigator).Content()
        }
        composable("onboarding") {
            Onboarding(appDatabase, navigator).Content()
        }
        composable("base") {
            Base(appDatabase).Content()
        }
        composable("login") {
            Login(appDatabase, navigator).Content()
        }
        composable("signup") {
            SignUp(appDatabase, navigator).Content()
        }

    }


}





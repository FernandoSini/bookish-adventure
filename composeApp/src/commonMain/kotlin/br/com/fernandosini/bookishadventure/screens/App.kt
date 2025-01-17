package br.com.fernandosini.bookishadventure.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.view.AccountScreen
import br.com.fernandosini.bookishadventure.screens.view.Base
import br.com.fernandosini.bookishadventure.screens.view.Home
import br.com.fernandosini.bookishadventure.screens.view.Login
import br.com.fernandosini.bookishadventure.screens.view.SearchScreen
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
fun App(appDatabase: AppDatabase){
    val navigator = rememberNavController()

    NavHost(
        navController = navigator,
        startDestination = "splash",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
    ){

        composable("splash") {
            Splash(appDatabase,navigator).Content()
        }
        composable("base") {
            Base(appDatabase).Content()
        }
      composable("login"){
          Login(appDatabase, navigator).Content()
      }

    }



}





package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bookishadventure.composeapp.generated.resources.DMSans_Light
import bookishadventure.composeapp.generated.resources.Res

import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.screens.ViewModel.BaseViewModel
import org.jetbrains.compose.resources.Font

class Base(private val appDatabase: AppDatabase) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val navigator = rememberNavController()
        val baseViewModel = viewModel<BaseViewModel> { BaseViewModel() }
        val currentDestination by navigator.currentBackStackEntryAsState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            isFloatingActionButtonDocked = false,
            floatingActionButtonPosition = FabPosition.End,

            floatingActionButton = {
                if (baseViewModel.bottomIndex.collectAsState().value == 0) {
                    FloatingActionButton(

                        onClick = {

                        },
                        backgroundColor = Color(0xffC6E2FF),
                        contentColor = Color.Black,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                } else {
                    null
                }
            },
            bottomBar = {
                BottomAppBar(
                   // windowInsets = WindowInsets.systemBars,
                    backgroundColor = Color.Black,
                    cutoutShape = CircleShape,
                    modifier = Modifier.padding(bottom = 20.dp),
                   // windowInsets = WindowInsets.navigationBars,
                    content = {
                        baseViewModel.bottomMenuItems.value.mapIndexed { index, element ->

                            BottomNavigationItem(

                                onClick = {

                                    baseViewModel.bottomIndex.value = index
                                    navigator.navigate(element["screen"].toString()) {
                                        //         if (navigator.graph.findStartDestination().route != element["screen"].toString()) {
                                        popUpTo(navigator.currentDestination?.route.toString()) {
                                            inclusive = true

                                        }
                                    }
                                    //   }


                                },
                                selected = currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true,
                                enabled = if (currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true) false else true,
                                selectedContentColor = Color(0xffC6E2FF),
                                unselectedContentColor = Color.White,

                                label = {
                                    Text(
                                        element["screen"].toString(),
                                        color = if (currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true)
                                            Color(
                                                0xffC6E2FF
                                            ) else Color.White,
                                        fontFamily = FontFamily(
                                            Font(Res.font.DMSans_Light)
                                        ),
                                        fontSize = 12.sp
                                    )
                                },
                                icon = {
                                    Icon(
                                        element["icon"] as ImageVector,
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp),
                                        // tint = Color.White
                                    )
                                },
                            )
                        }


                    })


            }) {

            NavHost(
                navController = navigator,
                startDestination = "home",
                modifier = Modifier,

                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(500)
                    )
                },
            ) {

                composable("home") {
                    Home(navController = navigator).Content()
                }
                composable("account") {
                    AccountScreen(navController = navigator).Content()
                }
                composable("favorite") {
                    Favorite(navController = navigator).Content()
                }
                composable("search") {
                    SearchScreen(navController = navigator).Content()
                }
            }
        }
    }
}

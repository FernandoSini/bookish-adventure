package br.com.flemis.bookishadventure.utils.widget

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.flemis.bookishadventure.features.profile.presentation.pages.AccountScreen
import br.com.flemis.bookishadventure.features.settings.presentation.pages.BillingMenu
import br.com.flemis.bookishadventure.features.profile.presentation.pages.EditProfile
import br.com.flemis.bookishadventure.presentation.pages.Flights
import br.com.flemis.bookishadventure.features.home.presentation.pages.Home
import br.com.flemis.bookishadventure.features.iap.presentation.pages.Paywall
import br.com.flemis.bookishadventure.presentation.pages.PolicyScreen
import br.com.flemis.bookishadventure.presentation.pages.SearchScreen
import br.com.flemis.bookishadventure.features.settings.presentation.pages.Settings
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class BottomNav {
    @Composable
    fun renderBottomNavItem(elementIcon: Any?) {
        return when (elementIcon) {
            is ImageVector -> {
                Icon(
                    elementIcon as ImageVector,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    // tint = Color.White
                )
            }

            is DrawableResource -> {
                Icon(
                    painterResource(elementIcon as DrawableResource),
                    contentDescription = null,
                    //  tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }

            else -> {}
        }
    }

    @Composable
    fun renderNavPages(navigator: NavHostController, themeViewModel: ThemeViewModel) {
        return NavHost(
            navController = navigator,
            startDestination = "home",
            modifier = Modifier,
            enterTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(500)
                )
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
                Home(navController = navigator).Content(themeViewModel)
            }
            composable("account") {
                AccountScreen(navController = navigator).Content()
            }
            composable("flights") {
                Flights(navController = navigator).Content()
            }
            composable("search") {
                SearchScreen(navController = navigator).Content()
            }
            composable("settings") {
                Settings(navController = navigator).Content(themeViewModel)
            }
            composable(
                //"policy/{policyType}",
                "policy"
                //  arguments = listOf(navArgument("policyType") { type = NavType.StringType })
            ) {
                // val policyType = it.arguments?.getString("policyType") ?: "privacy_policy"
                PolicyScreen(navController = navigator, it.savedStateHandle).Content()
            }
            composable("profile/edit") {
                EditProfile(navController = navigator, it.savedStateHandle).Content(themeViewModel)
            }
            composable("paywall") {
                Paywall(navController = navigator).Content()
            }
            composable("billing-history") {
                // BillingHistory(navController = navigator).Content()
            }
            composable("subscription-details") {
                // SubscriptionDetails(navController = navigator).Content()
            }
            composable("billing-menu"){
                 BillingMenu(navController = navigator).Content()
            }
            composable("change-billing") {
                // ChangeBilling(navController = navigator).Content()
            }
        }
    }

    companion object {
        private var instance = BottomNav()

        fun getInstance(): BottomNav {
            return instance
        }

    }
}


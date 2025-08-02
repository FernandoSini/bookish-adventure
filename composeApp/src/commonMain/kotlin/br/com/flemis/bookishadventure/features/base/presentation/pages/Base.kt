package br.com.flemis.bookishadventure.features.base.presentation.pages

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bookishadventure.composeapp.generated.resources.DMSans_Light
import bookishadventure.composeapp.generated.resources.Res
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.features.base.presentation.ui.viewmodel.BaseViewModel
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.utils.widget.BottomNav
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class Base() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(themeViewModel: ThemeViewModel) {
        val navigator = rememberNavController()
        val baseViewModel = viewModel<BaseViewModel> { BaseViewModel() }
        val currentDestination by navigator.currentBackStackEntryAsState()
        var showBottomNav =
            currentDestination?.destination?.route in baseViewModel.bottomMenuItems.value.map { it["screen"] }
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            backgroundColor = MaterialTheme.colorScheme.background,
            isFloatingActionButtonDocked = false,
            floatingActionButtonPosition = FabPosition.Companion.End,
            floatingActionButton = {
                if (baseViewModel.bottomIndex.collectAsState().value == 0) {
                    FloatingActionButton(
                        backgroundColor = Color(0xffC6E2FF),
                        contentColor = Color.Companion.Black,
                        onClick = {},
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.Companion.Black,
                                modifier = Modifier.Companion.size(20.dp)
                            )
                        }
                    )
                } else {
                    null
                }
            },
            bottomBar = {
                if (showBottomNav) {
                    BottomAppBar(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        cutoutShape = CircleShape,
                        modifier = if (getPlatform().name.lowercase()
                                .contains("ios")
                        ) Modifier.Companion.padding(bottom = 0.dp) else Modifier.Companion,
                        windowInsets = WindowInsets.Companion.navigationBars,
                        content = {
                            baseViewModel.bottomMenuItems.value.mapIndexed { index, element ->
                                BottomNavigationItem(
                                    onClick = {
                                        baseViewModel.bottomIndex.value = index
                                        navigator.navigate(element["screen"].toString()) {
                                            popUpTo(navigator.currentDestination?.route.toString()) {
                                                inclusive = true
                                            }
                                        }

                                    },
                                    selected = currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true,
                                    enabled = if (currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true) false else true,
                                    selectedContentColor = Color(0xffC6E2FF),
                                    unselectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                                    label = {
                                        Text(
                                            stringResource(element["label"] as StringResource),
                                            color = if (currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true)
                                                Color(
                                                    0xffC6E2FF
                                                ) else MaterialTheme.colorScheme.inversePrimary,
                                            fontFamily = FontFamily(
                                                Font(Res.font.DMSans_Light)
                                            ),
                                            fontSize = 12.sp
                                        )
                                    },
                                    icon = {
                                        BottomNav.Companion.getInstance().renderBottomNavItem(element["icon"])
                                    },
                                )
                            }
                        }
                    )
                }
            },
            content = {
                BottomNav.Companion.getInstance().renderNavPages(navigator, themeViewModel)
            }
        )
    }
}
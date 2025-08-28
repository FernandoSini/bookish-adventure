package br.com.flemis.bookishadventure.features.base.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.rememberMapState
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.MapsBottomSheet
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.NativeMapWidgetV2
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.utils.widget.BottomNav
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class Base() {
    @Preview
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Content(themeViewModel: ThemeViewModel) {
        val bottomSheetState = rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
            confirmValueChange = { true })
        val navigator = rememberNavController()
        val baseViewModel = viewModel<BaseViewModel> { BaseViewModel() }
        val currentDestination by navigator.currentBackStackEntryAsState()
        val scope = rememberCoroutineScope()
        val isOpenBottomSheet by rememberSaveable { mutableStateOf(false) }
        var showBottomNav =
            currentDestination?.destination?.route in baseViewModel.bottomMenuItems.value.map { it["screen"] }
        val disposeMapCallback by rememberSaveable { mutableStateOf<() -> Unit>({ }) }
        var isDisposedBottomSheet by remember { mutableStateOf(false) }

        MapsBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            sheetContentColor = MaterialTheme.colorScheme.secondary,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetGesturesEnabled = true,
            isDisposed = isDisposedBottomSheet,
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.9f).fillMaxWidth()
                        .fillMaxSize(),
                    // .padding( 16.dp),

                    content = {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            NativeMapWidgetV2(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                                mapState = rememberMapState(),
                            )

                            Box(
                                contentAlignment = Alignment.TopEnd,
                                modifier = Modifier.padding(top = 15.dp, end = 15.dp),
                                content = {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Close",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                scope.launch {
                                                    isDisposedBottomSheet = true
                                                    bottomSheetState.hide()
                                                    //disposeMapCallback.invoke()
                                                }
                                            }
                                    )
                                })
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    Modifier
                                        .width(40.dp)
                                        .height(4.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            shape = RoundedCornerShape(2.dp)
                                        ),

                                    )
                            }
                        }
                    })
            },
            screenContent = {
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
                        BottomNav.Companion.getInstance()
                            .renderNavPages(navigator, themeViewModel, isOpenBottomSheet, bottomSheetState, isDisposedBottomSheet)
                    },

                    )
            },
        )
    }
}

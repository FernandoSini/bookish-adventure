package br.com.flemis.bookishadventure.features.home.presentation.pages

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.welcome
import br.com.flemis.bookishadventure.features.home.presentation.ui.viewmodel.HomeViewModel
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import org.jetbrains.compose.resources.stringResource

class Home(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun Content(themeViewModel: ThemeViewModel) {
        val homeViewModel = viewModel<HomeViewModel> { HomeViewModel() }
        val scope = rememberCoroutineScope()
        val darkModeState by themeViewModel.state.collectAsState()
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            backgroundColor = Color.Companion.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,
                    // windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Companion.Transparent,

                        ),
                    navigationIcon = {
                        Column(
                            modifier = Modifier.Companion.padding(start = 15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                stringResource(Res.string.welcome),
                                style = MaterialTheme.typography.headlineSmall,
                            )
                            Text(
                                "Fernando",
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                        if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = if (getPlatform().name.lowercase()
                                            .contains("ios")
                                    ) Icons.AutoMirrored.Default.ArrowBackIos else Icons.AutoMirrored.Default.ArrowBack,
                                    tint = if (darkModeState.isDarkMode) Color.Companion.White else Color.Companion.Black,
                                    contentDescription = null,
                                    modifier = Modifier.Companion.size(20.dp)
                                )
                            }
                        }
                    },
                    title = {},

                    actions = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.Companion.padding(end = 15.dp).size(30.dp),
                            tint = if (darkModeState.isDarkMode) Color.Companion.White else Color.Companion.Black
                        )
                    },
                )
            },
        ) {
            FlowRow(
                maxLines = 1,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                //verticalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.Companion.horizontalScroll(
                    rememberScrollState(),
                    flingBehavior = ScrollableDefaults.flingBehavior(),


                    ).padding(10.dp)
            ) {
                homeViewModel.listMenuChips.value.mapIndexed { index, element ->
                    FilterChip(
                        onClick = {
                            homeViewModel.selectedChip.value = element

                        },
                        label = {
                            Text(
                                element,
                                style = MaterialTheme.typography.titleSmall.copy(Color.Companion.Unspecified)
                            )
                        },
                        modifier = Modifier.Companion.padding(it),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            selectedLabelColor = Color.Companion.Black,
                            selectedLeadingIconColor = Color.Companion.Black,
                            selectedTrailingIconColor = Color.Companion.Black,
                            disabledSelectedContainerColor = Color.Companion.Yellow,
                            disabledLabelColor = Color.Companion.White,
                            disabledContainerColor = Color.Companion.LightGray
                        ),
                        selected = element.lowercase() == homeViewModel.selectedChip.collectAsState().value.lowercase(),
                        enabled = true,
                        leadingIcon = {},
                        trailingIcon = {},
                        shape = CircleShape,
                        elevation = FilterChipDefaults.filterChipElevation(elevation = 1.dp),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = false, borderColor = Color.Companion.Unspecified,
                            selected = false,
                            selectedBorderColor = Color.Companion.Unspecified,
                            disabledBorderColor = Color.Companion.Unspecified,
                            disabledSelectedBorderColor = Color.Companion.Unspecified,
                            borderWidth = 1.dp,
                            selectedBorderWidth = 1.dp
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                    )
                }
            }
        }

    }


}
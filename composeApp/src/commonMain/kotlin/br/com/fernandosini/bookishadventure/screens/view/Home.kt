package br.com.fernandosini.bookishadventure.screens.view

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.welcome
import br.com.fernandosini.bookishadventure.getPlatform
import br.com.fernandosini.bookishadventure.screens.viewmodel.ThemeViewModel
import br.com.fernandosini.bookishadventure.screens.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue

class Home(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun Content(themeViewModel: ThemeViewModel) {
        val homeViewModel = viewModel<HomeViewModel> { HomeViewModel() }
        val scope = rememberCoroutineScope()
        val darkModeState by themeViewModel.state.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,
                    // windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,

                        ),
                    navigationIcon = {
                        Column(
                            modifier = Modifier.padding(start = 15.dp),
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
                                    tint =if(darkModeState.isDarkMode) Color.White else Color.Black,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    title = {},

                    actions = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 15.dp).size(30.dp),
                            tint = if(darkModeState.isDarkMode) Color.White else Color.Black
                        )


                    },

                    )
            },


            ) {

            FlowRow(
                maxLines = 1,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                //verticalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.horizontalScroll(
                    rememberScrollState(),
                    flingBehavior = ScrollableDefaults.flingBehavior()
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
                                style =  MaterialTheme.typography.titleSmall.copy(Color.Unspecified)
                            )
                        },
                        modifier = Modifier.padding(it),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            selectedLabelColor = Color.Black,
                            selectedLeadingIconColor = Color.Black,
                            selectedTrailingIconColor = Color.Black,
                            disabledSelectedContainerColor = Color.Yellow,
                            disabledLabelColor = Color.White,
                            disabledContainerColor = Color.LightGray
                        ),
                        selected = element.lowercase() == homeViewModel.selectedChip.collectAsState().value.lowercase(),
                        enabled = true,
                        leadingIcon = {},
                        trailingIcon = {},
                        shape = CircleShape,
                        elevation = FilterChipDefaults.filterChipElevation(elevation = 1.dp),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = false, borderColor = Color.Unspecified,
                            selected = false,
                            selectedBorderColor = Color.Unspecified,
                            disabledBorderColor = Color.Unspecified,
                            disabledSelectedBorderColor = Color.Unspecified,
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
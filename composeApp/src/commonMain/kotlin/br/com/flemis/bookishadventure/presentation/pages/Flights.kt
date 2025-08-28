package br.com.flemis.bookishadventure.presentation.pages

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.flights
import br.com.flemis.bookishadventure.features.maps.presentation.pages.MapsView
import br.com.flemis.bookishadventure.getPlatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

class Flights(
    private var navController: NavController,
    private var isOpenBottomSheet: Boolean,
    private var bottomSheetState: ModalBottomSheetState,
    private var isDisposedBottomSheet: Boolean
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val scope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Transparent,
            contentWindowInsets = WindowInsets.statusBars,
            topBar = {
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,
                    navigationIcon = {
                        if (getPlatform().name.lowercase().contains("android")) {
                            Text(
                                stringResource(Res.string.flights),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        } else {
                            null
                        }
                    },
                    title = {
                        if (getPlatform().name.lowercase().contains("ios")) {
                            Text(
                                stringResource(Res.string.flights),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        } else {
                            null
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                               /*              if (!isOpenBottomSheet) {
                                    isDisposedBottomSheet = false
                                    bottomSheetState.show()
                                } else {
                                    bottomSheetState.hide()
                                }*/
                                      navController.navigate("/maps"){
                                          popUpTo ("flights")
                                      }

                                }
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.TravelExplore,
                                contentDescription = "GPS",
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }
                )
            }
        ) {
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())
            ) { }

        }
    }
}
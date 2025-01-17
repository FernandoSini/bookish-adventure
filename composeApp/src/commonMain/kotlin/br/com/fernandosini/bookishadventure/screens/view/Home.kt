package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Light
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.plane_filled
import br.com.fernandosini.bookishadventure.getPlatform
import br.com.fernandosini.bookishadventure.screens.ViewModel.HomeViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

class Home(private var navController: NavController){

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun Content() {
        val homeViewModel = viewModel<HomeViewModel> { HomeViewModel() }
        val scope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(), backgroundColor = Color.Black,
            contentWindowInsets = WindowInsets.navigationBars,
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Column(modifier = Modifier.padding(start = 15.dp)) {
                            Text(
                                "Welcome",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        Res.font.DMSans_SemiBold
                                    )
                                )
                            )
                            Text(
                                "Fernando",
                                color = Color.White,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Light)),
                                fontSize = 18.sp
                            )
                        }
                        if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = if (getPlatform().name.lowercase()
                                            .contains("ios")
                                    ) Icons.AutoMirrored.Default.ArrowBackIos else Icons.AutoMirrored.Default.ArrowBack,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    title = {

                    },

                    actions = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )



                    },
                    windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                    ),
                )
            },



        ) {

            FlowRow(
                maxLines = 1,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                //verticalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
                    .padding(10.dp)
            ) {
                homeViewModel.listMenuChips.value.mapIndexed { index, element ->

                    FilterChip(
                        onClick = {

                            homeViewModel.selectedChip.value = element


                        },
                        label = { Text(element) },
                        modifier = Modifier.padding(it),
                        colors = FilterChipDefaults.filterChipColors(

                            containerColor = Color.White,
                            selectedContainerColor = Color(0xffC6E2FF),
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
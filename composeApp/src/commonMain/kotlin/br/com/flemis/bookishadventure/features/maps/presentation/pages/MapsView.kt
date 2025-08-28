package br.com.flemis.bookishadventure.features.maps.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.rememberMapState
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.NativeMapWidgetV2
import br.com.flemis.bookishadventure.getPlatform
import kotlinx.datetime.format.Padding
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class MapsView(private val navController: NavHostController) {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun renderMapsPage() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            //topBar = { MapsTopBar() },
            content = { paddingValues ->
                MapsContent(paddingValues)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MapsTopBar() {
        // Implement the top bar for the Maps page
        CenterAlignedTopAppBar(
            modifier = Modifier.background(Color.Transparent),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent.copy(
                    alpha = 0.0f,
                    red = 0f,
                    green = 0f,
                    blue = 0f
                )
            ),
            windowInsets = WindowInsets.statusBars.only(WindowInsetsSides.Top),
            title = {
                if (getPlatform().name.lowercase().contains("ios")) {
                    Text(
                        "cu",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                } else {
                    null
                }
            },
            navigationIcon = {
                if (getPlatform().name.lowercase().contains("android")) {
                    Row(content = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                tint = MaterialTheme.colorScheme.surfaceTint,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            "", style = TextStyle(
                                color = Color.White,
                                fontFamily = FontFamily(
                                    Font(Res.font.DMSans_SemiBold)
                                ),
                                fontSize = 20.sp,
                            ), modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)
                        )
                    })
                } else {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                            tint = MaterialTheme.colorScheme.surfaceTint,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            })
    }


    @Composable
    private fun MapsContent(paddingValues: PaddingValues) {
        val mapState = rememberMapState()
        Surface(

            contentColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            color = MaterialTheme.colorScheme.surface,
            content = {
                Box(Modifier.fillMaxSize()) {
                    NativeMapWidgetV2(
                        Modifier.fillMaxSize(), paddingValues, mapState
                    ){}
                    Box(
                        Modifier.fillMaxSize().padding(start = 15.dp).windowInsetsPadding(WindowInsets.statusBars),
                        contentAlignment = Alignment.TopStart
                    ) {
                        IconButton(
                            modifier = Modifier.background(
                                MaterialTheme.colorScheme.surface.copy(0.3f),
                                shape = CircleShape
                            ),
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBackIos,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp).padding(start = 5.dp),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }
                }
            }
        )
    }
}
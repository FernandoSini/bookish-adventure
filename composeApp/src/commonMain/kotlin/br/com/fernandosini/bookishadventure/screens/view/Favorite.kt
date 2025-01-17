package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import br.com.fernandosini.bookishadventure.getPlatform
import org.jetbrains.compose.resources.Font

class Favorite(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black,
            contentWindowInsets = WindowInsets.statusBars,
            topBar = {
                CenterAlignedTopAppBar(windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        if (getPlatform().name.lowercase().contains("android")) {
                            Text(
                                "Favorite places", style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily(
                                        Font(Res.font.DMSans_SemiBold)
                                    ),
                                    fontSize = 20.sp,
                                ),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        } else {
                            null
                        }
                    },
                    title = {
                        if (getPlatform().name.lowercase().contains("ios")) {
                            Text(
                                "Favorite places", style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily(
                                        Font(Res.font.DMSans_SemiBold)
                                    ),
                                    fontSize = 20.sp,
                                ),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        } else {
                            null
                        }
                    }
                )
            }
        ) {


        }
    }
}
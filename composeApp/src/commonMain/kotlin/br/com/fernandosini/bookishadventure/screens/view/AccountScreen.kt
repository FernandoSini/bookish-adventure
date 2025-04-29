package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.travel_activities
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class AccountScreen(private var navController: NavController) {

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets.safeContent,
            topBar = {
                CenterAlignedTopAppBar(
                    windowInsets = TopAppBarDefaults.windowInsets,
                    //expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor =  MaterialTheme.colorScheme.background,
                    ),
                    title = {},
                    modifier = Modifier,
                    navigationIcon = { null },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate("settings")
                            }
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    },
                )
            }
        ) {
            Surface(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                color = Color.Transparent
            ) {
                FlowColumn(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = it.calculateBottomPadding()),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {

                    Box(

                        Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier/*.padding(top = 25.dp)*/,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                Modifier.height(100.dp).width(100.dp)
                                    .background(Color.Yellow, shape = CircleShape),

                                ) {


                            }
                            Text(
                                text = "clebin94",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                            )
                            Text(
                                text = "São Paulo, BR",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Box(
                        Modifier.height(25.dp).fillMaxWidth().padding(horizontal = 15.dp)

                    ) {

                        Text(
                            text = stringResource(Res.string.travel_activities),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )
                    }

                    ActivityCard()


                }
            }
        }

    }

    @Composable
    @Preview
    fun ActivityCard() {
        Card(
            shape = RoundedCornerShape(size = 25.dp),
            modifier = Modifier.height(100.dp).fillMaxWidth().padding(horizontal = 10.dp),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Box(
                    Modifier.height(70.dp).width(70.dp)
                        .background(Color.Blue, shape = RoundedCornerShape(15.dp))
                ) {
                }
                Column(Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "Los angeles",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    )
                    Text(
                        text = "Account",
                        style = MaterialTheme.typography.titleMedium,

                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.WatchLater,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp).padding(end = 5.dp),
                            tint = Color.Gray,
                        )
                        Text(
                            text = "10/10/2021",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xff808080)
                            )
                        )
                    }
                }

            }
        }
    }
}
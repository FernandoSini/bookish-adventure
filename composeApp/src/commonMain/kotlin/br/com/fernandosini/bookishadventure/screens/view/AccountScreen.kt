package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import bookishadventure.composeapp.generated.resources.DMSans_Light
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

class AccountScreen(private var navController: NavController) {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black,
            contentWindowInsets = WindowInsets.safeContent,
            //   topBar = { TopAppBar(windowInsets = WindowInsets.statusBars){} }
        ) {

            FlowColumn(
                //horizontalArrangement = ,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(top = 50.dp, bottom = it.calculateBottomPadding())
                    .background(Color.Green),
                verticalArrangement = Arrangement.spacedBy(20.dp),


                ) {

                Box(

                    Modifier.fillMaxWidth().height(200.dp).background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(top = 25.dp),
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
                            style = TextStyle(
                                fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "São Paulo, BR",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(Res.font.DMSans_Regular)),
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        )
                    }
                }
                Box(
                    Modifier.height(25.dp).fillMaxWidth().padding(horizontal = 15.dp)

                ) {

                    Text(
                        text = "My Travel Activites",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    )
                }

                ActivityCard()


            }
        }

    }

    @Composable
    @Preview
    fun ActivityCard() {
        Card(
            shape = RoundedCornerShape(size = 25.dp),
            modifier = Modifier.height(100.dp).fillMaxWidth().padding(horizontal = 10.dp)

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
                        style = TextStyle(
                            fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "Account",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.WatchLater,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp).padding(end = 5.dp),
                            tint = Color.Gray,
                        )
                        Text(
                            text = "10/10/2021",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                                fontSize = 13.sp,
                                color = Color.LightGray
                            )
                        )
                    }
                }

            }
        }
    }
}
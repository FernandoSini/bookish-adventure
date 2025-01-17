package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.RoomDatabase
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.background_travel
import bookishadventure.composeapp.generated.resources.plane_filled
import bookishadventure.composeapp.generated.resources.travel_background
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import br.com.fernandosini.bookishadventure.repository.db.getRoomDatabase
import br.com.fernandosini.bookishadventure.screens.ViewModel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.seconds

class Splash(private val appDatabase: AppDatabase, private var navController: NavController) {


    @Composable
    fun Content() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val scope = rememberCoroutineScope()
        val splashViewModel = viewModel<SplashViewModel>() { SplashViewModel() }

        LifecycleEventEffect(event = Lifecycle.Event.ON_START, lifecycleOwner = lifecycleOwner) {
            scope.launch {
                splashViewModel.loading.value = true
                delay(4.seconds)
                navController.navigate("base") {
                    popUpTo("splash") { inclusive = true }
                }
                splashViewModel.loading.value = false
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Black) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painterResource(Res.drawable.background_travel),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight,
                    colorFilter = ColorFilter.tint(Color(0xffC6E2FF), BlendMode.Darken)

                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(Res.drawable.plane_filled),

                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.height(150.dp).width(150.dp).padding(bottom = 20.dp)
                                .rotate(320f)
                        )

                        Text(
                            "Bookish\nAdventure",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(Res.font.DMSans_Bold)
                                )
                            ),
                        )
                    }
                    if (splashViewModel.loading.value)
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 5.dp
                        )


                }
            }
        }
    }
}
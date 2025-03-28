package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.Res
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import org.jetbrains.compose.resources.Font

class SignUp(private val appDatabase: AppDatabase, private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val loginText = mutableStateOf<String>("")
        val passwordText = mutableStateOf<String>("")
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Black,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {

                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    windowInsets = TopAppBarDefaults.windowInsets,
                    navigationIcon = {
                        Box(
                            modifier = Modifier.padding(start = 15.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // IconButton(onClick = { navController.navigateUp() }) {
                            //     Icon(
                            //         imageVector = Icons.Filled.ArrowBack,
                            //         contentDescription = "Back",
                            //         tint = Color.White
                            //     )
                            // }
                        }
                    }
                )
            }

        ) {

            Surface(
                modifier = Modifier.fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
                color = Color.Transparent
            ) {

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(0.10f).wrapContentSize(Alignment.TopStart)
                        .padding(horizontal = 25.dp),
                    contentAlignment = Alignment.TopStart, propagateMinConstraints = false
                ) {

                    Text(
                        "Sign Up",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                            fontSize = 50.sp,
                            color = Color.White
                        ),
                    )
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.Center).fillMaxWidth().fillMaxHeight(0.65f),
                    propagateMinConstraints = true
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    ) {
                        LinedTextField(
                            value = loginText.value,
                            onValueChange = { it -> loginText.value = it },
                            placeHolder = "Name",
                            textColor = Color.White,
                            keyboardOptions = KeyboardOptions.Default
                        )
                        LinedTextField(
                            value = loginText.value,
                            onValueChange = { it -> loginText.value = it },
                            placeHolder = "Last Name",
                            keyboardOptions = KeyboardOptions.Default,
                            textColor = Color.White
                        )
                        LinedTextField(
                            value = passwordText.value,
                            onValueChange = { it -> passwordText.value = it },
                            placeHolder = "Username",
                            textColor = Color.White
                        )
                        LinedTextField(
                            value = passwordText.value,
                            onValueChange = { it -> passwordText.value = it },
                            placeHolder = "Email",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            textColor = Color.White
                        )
                        LinedTextField(
                            value = passwordText.value,
                            onValueChange = { it -> passwordText.value = it },
                            placeHolder = "Mobile Number",
                            textColor = Color.White,

                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        )
                        LinedTextField(
                            value = passwordText.value,
                            onValueChange = { it -> passwordText.value = it },
                            placeHolder = "Password",
                            textColor = Color.White,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick={}){
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Visibility",
                                    tint = Color.White
                                )
                            }}
                        )
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = {},
                            ) {
                                Text(
                                    "Forgot Password?",
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(Res.font.DMSans_Medium)),
                                        fontSize = 13.sp,
                                        color = Color(0xff0B85F7)
                                    )
                                )
                            }
                        }

                    }
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.BottomCenter).fillMaxWidth()
                        .fillMaxHeight(0.25f), propagateMinConstraints = true
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomElevatedButton(
                            "SignUp", TextStyle(
                                fontSize = 13.sp, fontFamily = FontFamily(
                                    Font(Res.font.DMSans_Bold)
                                ), color = Color.White
                            ), {}
                        )
                        Spacer(Modifier.height(20.dp))
                        CustomOutlinedButton(
                            "Login", onClick = {}, textStyle = TextStyle(
                                fontSize = 13.sp, fontFamily = FontFamily(
                                    Font(Res.font.DMSans_Bold)
                                ), color = Color.White
                            )
                        )

                    }


                }


            }
        }
    }

}



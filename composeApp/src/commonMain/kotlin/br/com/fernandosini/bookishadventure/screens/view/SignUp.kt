package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowHeightSizeClass
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.forgot_password
import bookishadventure.composeapp.generated.resources.terms_of_service
import bookishadventure.composeapp.generated.resources.terms_service_button
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class SignUp(private val appDatabase: AppDatabase, private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val textfields = MutableList(6) { rememberSaveable { mutableStateOf<String>("") } }
        var passwordVisible by rememberSaveable { mutableStateOf<Boolean>(false) }
        val keyBoardController = LocalSoftwareKeyboardController.current
        var isScrollable by rememberSaveable { mutableStateOf<Boolean>(false) }
        var focusRequester = rememberSaveable { FocusRequester() }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
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
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.Center).fillMaxWidth().fillMaxHeight(0.65f),
                    propagateMinConstraints = true
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(horizontal = 25.dp).verticalScroll(
                            rememberScrollState(),
                            enabled = isScrollable,
                            flingBehavior = ScrollableDefaults.flingBehavior()
                        )
                    ) {
                        LinedTextField(
                            value = textfields[0].value,
                            onValueChange = { it -> textfields[0].value = it },
                            placeHolder = "Name",
                            textColor = Color.White,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            value = textfields[1].value,
                            onValueChange = { it -> textfields[1].value = it },
                            placeHolder = "Lastname",
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                            textColor = Color.White
                        )
                        LinedTextField(
                            value = textfields[2].value,
                            onValueChange = { it -> textfields[2].value = it },
                            placeHolder = "Username",
                            textColor = Color.White,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            value = textfields[3].value,
                            onValueChange = { it -> textfields[3].value = it },
                            placeHolder = "Email",
                            textColor = Color.White,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            value = textfields[4].value,
                            onValueChange = { it ->
                                textfields[4].value = it
                                if (textfields[4].value.length >= 9) {
                                    focusRequester.requestFocus()
                                }
                            },
                            placeHolder = "Mobile Number",
                            textColor = Color.White,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next

                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
                            value = textfields[5].value,
                            onValueChange = { it -> textfields[5].value = it },
                            placeHolder = "Password",
                            textColor = Color.White,
                            passwordVisible = passwordVisible,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                            maxLines = 1,
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }
                                ) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = "Visibility",
                                        tint = MaterialTheme.colorScheme.inversePrimary
                                    )
                                }
                            }
                        )
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            TextButton(
                                onClick = {
                                    navController.let { it ->
                                        it.currentBackStackEntry?.savedStateHandle?.set(
                                            "policyType",
                                            "terms_of_service"
                                        )
                                        it.navigate("policy")
                                    }
                                },

                                ) {
                                Text(
                                    stringResource(Res.string.terms_service_button) + " ",
                                    modifier = Modifier.clickable(false) { null },
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(Res.font.DMSans_Medium)),
                                        fontSize = 13.sp,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    stringResource(Res.string.terms_of_service),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(Res.font.DMSans_Medium)),
                                        fontSize = 13.sp,
                                        color = Color(0xff0B85F7),
                                        textDecoration = TextDecoration.Underline
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
                            ),
                            onClick = {
                                navController.navigate("verify-code") {
                                    /* popUpTo(navController.currentDestination?.route.toString()) {
                                         inclusive = true
                                     }*/
                                }
                            }
                        )
                        Spacer(Modifier.height(20.dp))
                        CustomOutlinedButton(
                            "Login",
                            onClick = {
                                navController.navigate("login") {
                                    popUpTo(navController.currentDestination?.route.toString()) {
                                        inclusive = true
                                    }
                                }
                            },
                            textStyle = MaterialTheme.typography.titleSmall


                            )

                    }


                }


            }
        }
    }

}



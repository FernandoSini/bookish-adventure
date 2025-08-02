package br.com.flemis.bookishadventure.features.auth.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.forgot_password
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.AuthViewModel
import br.com.flemis.bookishadventure.utils.widget.CustomElevatedButton
import br.com.flemis.bookishadventure.utils.widget.CustomOutlinedButton
import br.com.flemis.bookishadventure.utils.widget.LinedTextField
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class Login(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val authViewModel = koinViewModel<AuthViewModel>()
        val authState by authViewModel.state.collectAsState()

        val loginText = mutableStateOf<String>("")
        val passwordText = mutableStateOf<String>("")
        var passwordVisible by rememberSaveable { mutableStateOf<Boolean>(false) }
        var focusRequester = remember { FocusRequester() }
        var snackbarHostState = remember { SnackbarHostState() }
        var scope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp)
                            .wrapContentSize(Alignment.BottomCenter),
                        snackbarData = data,
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.typography.labelSmall.color,
                        actionColor = MaterialTheme.colorScheme.surfaceTint,
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    windowInsets = TopAppBarDefaults.windowInsets,
                    title = {},
                    navigationIcon = {}
                )
            },
            content = {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.fillMaxSize()
                        .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
                    content = {
                        BoxWithConstraints(
                            contentAlignment = Alignment.TopStart,
                            propagateMinConstraints = false,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f)
                                .wrapContentSize(Alignment.TopStart).padding(horizontal = 25.dp),
                            content = {
                                Text(
                                    "Login", style = MaterialTheme.typography.displayMedium
                                )
                            }
                        )
                        BoxWithConstraints(
                            Modifier.wrapContentSize(Alignment.Center).fillMaxWidth().fillMaxHeight(0.5f),
                            propagateMinConstraints = true
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.padding(horizontal = 25.dp),
                                content = {
                                    LinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = loginText.value,
                                        onValueChange = { it -> loginText.value = it },
                                        placeHolder = "Email",
                                        textColor = Color.White,
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                                        ),
                                        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
                                    )
                                    LinedTextField(
                                        modifier = Modifier.focusRequester(focusRequester = focusRequester)
                                            .fillMaxWidth(),
                                        passwordVisible = passwordVisible,
                                        value = passwordText.value,
                                        onValueChange = { it -> passwordText.value = it },
                                        placeHolder = "Password",
                                        textColor = Color.White,
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                                        ),
                                        trailingIcon = {
                                            IconButton(
                                                onClick = {
                                                    passwordVisible = !passwordVisible
                                                },
                                                content = {
                                                    Icon(
                                                        imageVector = if (passwordVisible) Icons.Filled.Visibility
                                                        else Icons.Filled.VisibilityOff,
                                                        contentDescription = "Visibility",
                                                        tint = MaterialTheme.colorScheme.inversePrimary
                                                    )
                                                }
                                            )
                                        },
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth(),
                                        content = {
                                            TextButton(
                                                onClick = {},
                                                content = {
                                                    Text(
                                                        stringResource(Res.string.forgot_password),
                                                        style = MaterialTheme.typography.titleSmall.copy(
                                                            fontFamily = FontFamily(Font(Res.font.DMSans_Medium)),
                                                            fontSize = 13.sp,
                                                            color = Color(0xff0B85F7),
                                                        ),
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        BoxWithConstraints(
                            Modifier.wrapContentSize(Alignment.BottomCenter).fillMaxWidth()
                                .fillMaxHeight(0.25f), propagateMinConstraints = true,
                            content = {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    content = {
                                        if (authState.isLoading) {
                                            CircularProgressIndicator(
                                                color = Color.Red,
                                                strokeWidth = 5.dp
                                            )
                                        } else {
                                            CustomElevatedButton(
                                                "Login",
                                                TextStyle(
                                                    fontSize = 13.sp, fontFamily = FontFamily(
                                                        Font(Res.font.DMSans_Bold)
                                                    ), color = Color.White
                                                ),
                                                onClick = {
                                                    loginClick(
                                                        authViewModel = authViewModel,
                                                        loginText = loginText.value,
                                                        passwordText = passwordText.value,
                                                        snackbarHostState = snackbarHostState,
                                                        navController = navController
                                                    )
                                                },
                                            )
                                        }
                                        Spacer(Modifier.height(20.dp))
                                        CustomOutlinedButton(
                                            "Register",
                                            textStyle = MaterialTheme.typography
                                                .titleSmall.copy(MaterialTheme.colorScheme.inversePrimary),
                                            onClick = {
                                                registerClick(
                                                    authViewModel = authViewModel,
                                                    snackbarHostState = snackbarHostState,
                                                    navController = navController
                                                )
                                            },
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}

fun loginClick(
    authViewModel: AuthViewModel,
    loginText: String,
    passwordText: String,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    authViewModel.login(
        body = hashMapOf<String, String>(
            "email" to loginText,
            "password" to passwordText
        ),
        snackbarHostState
    )
    if (authViewModel.state.value.userState != null) {
        navController.navigate("home") {
            popUpTo("login") {
                inclusive = true
            }
        }
    }
}

fun registerClick(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    // Navigate to the registration screen
    navController.navigate("signUp") {
        popUpTo("login") {
            inclusive = true
        }
    }
}




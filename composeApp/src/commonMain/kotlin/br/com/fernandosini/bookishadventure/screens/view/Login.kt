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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.forgot_password
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class Login(private val appDatabase: AppDatabase, private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val loginText = mutableStateOf<String>("")
        val passwordText = mutableStateOf<String>("")
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
                    })
            }

        ) {

            Surface(
                modifier = Modifier.fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
                color = Color.Transparent
            ) {

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f)
                        .wrapContentSize(Alignment.TopStart).padding(horizontal = 25.dp),
                    contentAlignment = Alignment.TopStart,
                    propagateMinConstraints = false
                ) {

                    Text(
                        "Login",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.Center).fillMaxWidth().fillMaxHeight(0.5f),
                    propagateMinConstraints = true
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    ) {
                        LinedTextField(
                            value = loginText.value,
                            onValueChange = { it -> loginText.value = it },
                            placeHolder = "Email",
                            textColor = Color.White,
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            value = passwordText.value,
                            onValueChange = { it -> passwordText.value = it },
                            placeHolder = "Password",
                            textColor = Color.White,
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),

                            )
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = {},
                            ) {
                                Text(
                                    stringResource(Res.string.forgot_password),
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
                            "Login", TextStyle(
                                fontSize = 13.sp, fontFamily = FontFamily(
                                    Font(Res.font.DMSans_Bold)
                                ), color = Color.White

                            ), {})
                        Spacer(Modifier.height(20.dp))
                        CustomOutlinedButton(
                            "Register", onClick = {
                                navController.navigate("signUp") {
                                    popUpTo("login") {
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


@Composable
fun LinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    textColor: Color,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    passwordVisible: Boolean? = null,
    modifier: Modifier = Modifier.fillMaxWidth().height(60.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = 1,
    minLines: Int = 1,
    singleLine: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeHolder,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                ),
            )
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = if (passwordVisible != null && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.outlineVariant,
            focusedTextColor = textColor,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedTextColor = textColor
        ),
        supportingText = supportingText
    )
}

@Composable
fun CustomElevatedButton(
    placeholder: String,
    textStyle: TextStyle = TextStyle(
        fontSize = 13.sp, fontFamily = FontFamily(
            Font(Res.font.DMSans_Bold)
        ), color = Color.White
    ),
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth().height(50.dp),
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xff3449A7),
            contentColor = Color.White
        ),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,


            ) {
            if (trailingIcon != null) {
                Spacer(Modifier.width(30.dp))
            } else {
                Spacer(
                    Modifier.width(IntrinsicSize.Min)
                )
            }
            Text(placeholder, softWrap = true, style = textStyle)
            if (trailingIcon != null) {
                trailingIcon()
            } else {
                Spacer(Modifier.width(IntrinsicSize.Min))
            }

        }
    }
}

@Composable
fun CustomOutlinedButton(
    placeholder: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    containerColor: Color = Color.Transparent,
    contentColor: Color = Color.White,
    disabledContentColor: Color = Color.Unspecified,
    disabledContainerColor: Color = Color.Unspecified,
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, contentColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor

        )
    ) {
        Text(placeholder, softWrap = true, style = textStyle)
    }
}
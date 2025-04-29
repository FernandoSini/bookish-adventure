package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.didnt_receive_code
import bookishadventure.composeapp.generated.resources.resend
import bookishadventure.composeapp.generated.resources.settings
import bookishadventure.composeapp.generated.resources.verify_code_continue
import bookishadventure.composeapp.generated.resources.verify_code_description
import bookishadventure.composeapp.generated.resources.verify_code_title
import br.com.fernandosini.bookishadventure.getPlatform
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class VerifyCodeScreen(appDatabase: AppDatabase, private var navigator: NavHostController) {

    val textFieldValues = MutableList(5, init = { mutableStateOf("") })


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        //val (item1, item2, item3, item4, item5) = remember { FocusRequester.createRefs() }
        val focusRequesters = List(textFieldValues.size) { FocusRequester() }
        // val focusManager = LocalFocusManager.current
        val clipboardManager = LocalClipboard.current
        val keyBoardController = LocalSoftwareKeyboardController.current
        LaunchedEffect(Unit) {
            focusRequesters.first().requestFocus()
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    windowInsets = TopAppBarDefaults.windowInsets,
                    navigationIcon = {
                        Box(
                            modifier = Modifier.padding(start = 15.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (navigator.previousBackStackEntry != null) {
                                IconButton(onClick = { navigator.popBackStack() }) {
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
                        }
                    })
            }

        ) {

            Surface(
                modifier = Modifier.fillMaxSize().padding(
                    top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()
                ), color = Color.Transparent
            ) {

                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.TopCenter).fillMaxWidth().fillMaxHeight(.25f)

                ) {
                    Column(
                        Modifier.padding(25.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.verify_code_title),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                            ),
                        )
                        Text(
                            text = stringResource(Res.string.verify_code_description),
                            softWrap = true,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Regular))
                            ),
                        )
                    }
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize(Alignment.Center).fillMaxWidth().fillMaxHeight(.5f),
                    propagateMinConstraints = true,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        textFieldValues.map { state ->

                            LinedTextField(
                                modifier = Modifier.padding(horizontal = 5.dp)
                                    .width(55.dp)
                                    .focusRequester(focusRequesters[textFieldValues.indexOf(state)])
                                    .onPreviewKeyEvent { event ->

                                        if (event.key == Key.Backspace) {
                                            if (state.value.isEmpty() && textFieldValues.indexOf(
                                                    state
                                                ) > 0
                                            ) {
                                                state.value = ""
                                                focusRequesters[textFieldValues.indexOf(state) - 1].requestFocus()
                                            } else {
                                                state.value = ""
                                            }
                                            true
                                        } else {
                                            false
                                        }
                                    },
                                onValueChange = { newValue ->

                                    if (newValue.length <= 1) {
                                        state.value = newValue
                                        if (newValue.isNotEmpty() && textFieldValues.indexOf(state) < textFieldValues.size - 1) {
                                            focusRequesters[textFieldValues.indexOf(state) + 1].requestFocus()
                                        } else if (newValue.isEmpty() && textFieldValues.indexOf(
                                                state
                                            ) > 0
                                        ) {
                                            focusRequesters[textFieldValues.indexOf(state) - 1].requestFocus()
                                        }
                                    } else {
                                        val currentIndex = textFieldValues.indexOf(state)
                                        val remainingValues =
                                            newValue.take(textFieldValues.size - currentIndex)
                                        remainingValues.forEachIndexed { index, char ->
                                            textFieldValues[currentIndex + index].value =
                                                char.toString()
                                        }
                                        if (currentIndex + remainingValues.length < textFieldValues.size) {
                                            focusRequesters[currentIndex + remainingValues.length].requestFocus()
                                        }
                                    }
                                },
                                value = state.value,
                                placeHolder = "",
                                textColor = Color.White,
                                trailingIcon = null,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Unspecified,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true,
                                textStyle = TextStyle(textAlign = TextAlign.Center),
                                keyboardActions = KeyboardActions(onNext = {
                                    if (textFieldValues.last() == state) {
                                        // focusManager.clearFocus(true)
                                        focusRequesters.last().freeFocus()
                                        keyBoardController?.hide()
                                    } else {
                                        // focusManager.moveFocus(FocusDirection.Next)
                                    }
                                    // FocusManager.moveFocus(FocusDirection.Next)
                                })
                            )
                        }
                    }
                }
                BoxWithConstraints(
                    Modifier.wrapContentSize().fillMaxWidth().fillMaxHeight(.25f),
                    contentAlignment = Alignment.TopCenter,
                    propagateMinConstraints = true,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomElevatedButton(
                            placeholder = stringResource(Res.string.verify_code_continue),
                            onClick = { println(textFieldValues.map { it.value }) },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
                            trailingIcon = {
                                Box(
                                    Modifier.size(30.dp).background(
                                        Color(0xff2C3F96), shape = RoundedCornerShape(5.dp),
                                    ).align(Alignment.End), contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (getPlatform().name.lowercase()
                                                .contains("ios")
                                        ) Icons.AutoMirrored.Default.ArrowForwardIos else Icons.AutoMirrored.Default.ArrowForward,
                                        tint = Color.White,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)

                                    )
                                }
                            })
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(Res.string.didnt_receive_code),
                                softWrap = true,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(Res.font.DMSans_Regular))
                                ),
                            )
                            TextButton(
                                onClick = {
                                },
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,


                                    ),
                            ) {
                                Text(
                                    text = stringResource(Res.string.resend),
                                    style = TextStyle(
                                        color = Color(0xff0B85F7),
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold))
                                    ),
                                )
                            }

                        }
                    }

                }
            }
        }

    }
}


/*

isso aqui é o codigo gerado pelo gepeto, que funciona, mas ja ajustei o meu
*   if (event.key == Key.Backspace) {
                                        if (state.value.isEmpty() && textFieldValues.indexOf(state) > 0) {
                                            state.value = ""
                                            focusRequesters[textFieldValues.indexOf(state) - 1].requestFocus()
                                        } else {
                                            state.value = ""
                                        }
                                        true
                                    } else {
                                        false
                                    }
                                },
                                onValueChange = {
                                    if (it.length <= 1) {
                                        state.value = it
                                        if (it.isNotEmpty() && textFieldValues.indexOf(state) < textFieldValues.size - 1) {
                                            focusRequesters[textFieldValues.indexOf(state) + 1].requestFocus()
                                        } else if (it.isEmpty() && textFieldValues.indexOf(state) > 0) {
                                            focusRequesters[textFieldValues.indexOf(state) - 1].requestFocus()
                                        }
                                    } else {
                                        if (textFieldValues.indexOf(state) < textFieldValues.size - 1) {
                                            focusRequesters[textFieldValues.indexOf(state) + 1].requestFocus()
                                        }
                                    }
                                },
* */
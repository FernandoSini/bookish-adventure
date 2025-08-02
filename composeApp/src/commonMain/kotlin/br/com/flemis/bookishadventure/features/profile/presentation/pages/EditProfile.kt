package br.com.flemis.bookishadventure.features.profile.presentation.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.edit_profile
import bookishadventure.composeapp.generated.resources.save
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.getPlatform
import br.com.flemis.bookishadventure.utils.widget.CustomElevatedButton
import br.com.flemis.bookishadventure.utils.widget.LinedTextField
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class EditProfile(
    private val navController: NavController, private var savedStateHandle: SavedStateHandle?
) {

    private val userId =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("userId") ?: ""

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(themeViewModel: ThemeViewModel) {
        //var textFieldState = rememberSaveable { mutableStateOf<String>("") }
        var focusRequester = remember { FocusRequester() }
        var textFields = MutableList(6) { rememberSaveable { mutableStateOf<String>("") } }
        var passwordVisible by rememberSaveable { mutableStateOf<Boolean>(false) }
        val darkModeState by themeViewModel.state.collectAsState()
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Companion.Transparent
                    ), title = {
                        if (getPlatform().name.lowercase().contains("ios")) {
                            Text(
                                stringResource(Res.string.edit_profile),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                modifier = Modifier.Companion.padding(start = 10.dp)
                            )
                        } else {
                            null
                        }

                    }, modifier = Modifier.Companion, navigationIcon = {
                        if (getPlatform().name.lowercase().contains("android")) {
                            Row() {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        tint = MaterialTheme.colorScheme.surfaceTint,
                                        contentDescription = null,
                                        modifier = Modifier.Companion.size(20.dp)
                                    )
                                }

                                Text(
                                    stringResource(Res.string.edit_profile),
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                    modifier = Modifier.Companion.padding(start = 10.dp)
                                        .align(Alignment.Companion.CenterVertically)
                                )
                            }
                        } else {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                                    tint = MaterialTheme.colorScheme.surfaceTint,
                                    contentDescription = null,
                                    modifier = Modifier.Companion.size(20.dp)
                                )
                            }
                        }

                    })
            }) {

            Surface(
                modifier = Modifier.Companion.fillMaxSize().padding(
                    bottom = it.calculateBottomPadding(), top = it.calculateTopPadding()
                ), color = Color.Companion.Transparent
            ) {
                BoxWithConstraints(
                    modifier = Modifier.Companion.fillMaxWidth().fillMaxHeight(0.25f)
                        .wrapContentSize(Alignment.Companion.TopCenter).padding(top = 20.dp),
                    propagateMinConstraints = false,
                    contentAlignment = Alignment.Companion.TopCenter
                ) {

                    Box(
                        modifier = Modifier.Companion.size(100.dp).border(
                            BorderStroke(3.dp, Color(0xff3449A7)), shape = CircleShape
                        ).clickable(
                            onClick = {},
                            role = Role.Companion.Button,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }),
                        content = {
                            Badge(
                                Modifier.Companion.size(20.dp).offset(
                                    x = (-3).dp, y = (-3).dp
                                ).clip(CircleShape).align(Alignment.Companion.BottomEnd),
                                // Ajuste fino para posicionar sobre a borda
                                backgroundColor = Color(0xff3449A7),
                            ) {
                                Icon(Icons.Default.Edit, null, tint = Color.Companion.White)
                            }
                        },
                    )
                }



                BoxWithConstraints(
                    Modifier.Companion.fillMaxWidth().fillMaxHeight(0.5f).wrapContentSize(Alignment.Companion.Center),
                    propagateMinConstraints = true
                ) {
                    Column(
                        Modifier.Companion.padding(horizontal = 25.dp).verticalScroll(
                            rememberScrollState(),
                            flingBehavior = ScrollableDefaults.flingBehavior()
                        )
                    ) {
                        Row(
                            modifier = Modifier.Companion.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Companion.CenterVertically,
                        ) {
                            LinedTextField(
                                modifier = Modifier.Companion.weight(1f),
                                value = textFields[0].value,
                                placeHolder = "Name",
                                textColor = Color.Companion.White,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Companion.Next),
                                onValueChange = { textFields[0].value = it },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                    fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                                ),
                            )

                            LinedTextField(
                                modifier = Modifier.Companion.weight(1f),
                                value = textFields[1].value,
                                placeHolder = "Lastname",
                                textColor = Color.Companion.White,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Companion.Next),
                                onValueChange = { textFields[1].value = it },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                    fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                                ),
                            )
                        }
                        LinedTextField(
                            modifier = Modifier.Companion.fillMaxWidth(),
                            value = textFields[2].value,
                            placeHolder = "Username",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Companion.Next
                            ),
                            textColor = Color.Companion.White,
                            onValueChange = { textFields[2].value = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            modifier = Modifier.Companion.fillMaxWidth(),
                            value = textFields[3].value,
                            placeHolder = "Email",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Companion.Email, imeAction = ImeAction.Companion.Next
                            ),
                            textColor = Color.Companion.White,
                            onValueChange = { textFields[3].value = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                        )
                        LinedTextField(
                            modifier = Modifier.Companion.fillMaxWidth(),
                            value = textFields[4].value,
                            placeHolder = "Mobile number",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Companion.Text, imeAction = ImeAction.Companion.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                if (textFields[4].value.isEmpty() || (textFields[4].value.length >= 11 && textFields[4].value.length <= 15)) {

                                    focusRequester.requestFocus()
                                }
                            }),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                            textColor = Color.Companion.White,
                            onValueChange = {
                                textFields[4].value = it.filter {
                                    it.isDigit() || it in listOf('(', ')', '-', ' ', '+', '/')
                                }.trim()

                            },
                            trailingIcon = {
                                Text(
                                    "${textFields[4].value.length}/20",
                                    modifier = Modifier.Companion.padding(top = 10.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(Res.font.DMSans_Medium)),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Companion.W600,
                                        color = MaterialTheme.colorScheme.inversePrimary
                                    ),
                                )

                            })
                        LinedTextField(
                            modifier = Modifier.Companion.fillMaxWidth()
                                .focusRequester(focusRequester),
                            value = textFields[5].value,
                            placeHolder = "Password",
                            passwordVisible = passwordVisible,
                            textColor = Color.Companion.White,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Companion.Done),
                            onValueChange = { textFields[5].value = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = "Visibility",
                                        tint = MaterialTheme.colorScheme.inversePrimary
                                    )
                                }
                            })
                    }
                }

                BoxWithConstraints(
                    modifier = Modifier.Companion.fillMaxWidth().height(300.dp)
                        .padding(horizontal = 25.dp, vertical = 10.dp).wrapContentSize(
                            Alignment.Companion.BottomCenter
                        ),
                    propagateMinConstraints = true,
                ) {
                    CustomElevatedButton(
                        stringResource(Res.string.save),
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(
                                Font(Res.font.DMSans_Bold)
                            ),
                        ),

                        onClick = {

                        },
                    )

                }
            }
        }

    }
}
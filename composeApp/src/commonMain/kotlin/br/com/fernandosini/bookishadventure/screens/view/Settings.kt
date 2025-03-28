package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import bookishadventure.composeapp.generated.resources.about
import bookishadventure.composeapp.generated.resources.billing
import bookishadventure.composeapp.generated.resources.billing_policy
import bookishadventure.composeapp.generated.resources.feedback
import bookishadventure.composeapp.generated.resources.logout
import bookishadventure.composeapp.generated.resources.privacy_policy
import bookishadventure.composeapp.generated.resources.settings
import bookishadventure.composeapp.generated.resources.support
import bookishadventure.composeapp.generated.resources.terms_of_service
import bookishadventure.composeapp.generated.resources.terms_of_use
import br.com.fernandosini.bookishadventure.getPlatform
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class Settings(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { null },
                    modifier = Modifier,
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = if (getPlatform().name.lowercase()
                                        .contains("ios")
                                ) Icons.AutoMirrored.Default.ArrowBackIos else Icons.AutoMirrored.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    windowInsets = TopAppBarDefaults.windowInsets,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),

                    )
            }

        ) {
            Surface(
                modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding()),
                color = Color.Transparent
            ) {
                FlowColumn(
                    modifier = Modifier.fillMaxSize().padding(
                        //top = it.calculateTopPadding(),
                        start = it.calculateStartPadding(LayoutDirection.Ltr)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(start = 30.dp)) {
                        Text(
                            text = stringResource(Res.string.settings),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                            ),
                        )
                    }
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        RoundedButton(
                            color = Color(0xffFFC107),
                            icon = Icons.Default.Info,
                            iconColor = Color(0xffA3A0A0),
                            onClick = { println(getPlatform().appVersion) },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.about)
                        )

                        RoundedButton(
                            color = Color(0xffC6E2FF),
                            icon = Icons.AutoMirrored.Filled.Help,
                            iconColor = Color(0xffA3A0A0),
                            onClick = { },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.support)
                        )
                        RoundedButton(
                            color = Color.Blue,
                            icon = Icons.Default.RecordVoiceOver,
                            iconColor = Color(0xffA3A0A0),
                            onClick = { },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.feedback)
                        )
                        RoundedButton(
                            color = Color.Black,
                            icon = Icons.Default.Policy,
                            iconColor = Color(0xffA3A0A0),
                            onClick = {
                                navController.let { it ->
                                    it.currentBackStackEntry?.savedStateHandle?.set(
                                        "policyType",
                                        "privacy_policy"
                                    )
                                    it.navigate("policy")
                                }

                            },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.privacy_policy)
                        )
                        RoundedButton(
                            color = Color.DarkGray,
                            icon = Icons.Default.SafetyCheck,
                            iconColor = Color(0xffA3A0A0),
                            onClick = {
                                navController.let { it ->
                                    it.currentBackStackEntry?.savedStateHandle?.set(
                                        "policyType",
                                        "terms_of_service"
                                    )
                                    it.navigate("policy")
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.terms_of_service)
                        )
                        RoundedButton(
                            color = Color.Magenta,
                            icon = Icons.Default.PrivacyTip,
                            iconColor = Color(0xffA3A0A0),
                            onClick = {
                                navController.let { it ->
                                    it.currentBackStackEntry?.savedStateHandle?.set(
                                        "policyType",
                                        "billing_policy"
                                    )
                                    it.navigate("policy")
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.billing_policy)
                        )
                        RoundedButton(
                            color = Color.LightGray,
                            icon = Icons.Default.CreditCard,
                            iconColor = Color(0xffA3A0A0),
                            onClick = { },
                            shape = RoundedCornerShape(10.dp),
                            text = stringResource(Res.string.billing)
                        )
                        Spacer(Modifier.height(40.dp))
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red,
                                disabledContentColor = Color.LightGray,
                            ),

                            content = {
                                Text(
                                    stringResource(Res.string.logout),
                                    fontSize = 15.sp,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                                    )
                                )
                            },
                            onClick = { },
                            modifier = Modifier
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedButton(
    color: Color,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    text: String,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(0xff1E1E1E),
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 15.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize().padding(start = 15.dp)
        ) {

            Box(
                Modifier.height(30.dp).width(30.dp).background(color = color, shape = shape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,

                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    // tint = Color(0xffA3A0A0),
                    tint = Color.White,
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp),
                text = text,
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
                maxLines = 1,
                style = TextStyle(textIndent = TextIndent(10.sp))

            )


        }
    }
}


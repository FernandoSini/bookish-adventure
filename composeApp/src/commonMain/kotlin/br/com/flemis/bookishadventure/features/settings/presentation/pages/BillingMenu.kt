package br.com.flemis.bookishadventure.features.settings.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.BillingViewModel
import br.com.flemis.bookishadventure.utils.widget.RoundedButton
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class BillingMenu(private val navController: NavController) {

    @Composable
    fun Content() {

        val billingViewModel = koinViewModel<BillingViewModel>()
        val billingMenuState by billingViewModel.menuList.collectAsState()
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            containerColor = Color.Companion.Black,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            content = {
                Surface(
                    Modifier.Companion.fillMaxSize().padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    ),
                    color = Color.Companion.Transparent,
                    content = {
                        Column(
                            Modifier.Companion.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalAlignment = Alignment.Companion.CenterHorizontally,
                            content = {

                                billingMenuState.mapIndexed { index, item ->
                                    RoundedButton(
                                        shape = RoundedCornerShape(10.dp),
                                        text = stringResource(item["label"] as StringResource),
                                        cardColor = MaterialTheme.colorScheme.primaryContainer,
                                        color = Color(0xffFFC107),
                                        icon = item["icon"] as ImageVector,
                                        iconColor = Color(0xffA3A0A0),
                                        onClick = {
                                            navController.navigate(item["screen"] as String)
                                        }
                                    )
                                }
                            }
                        )
                    }
                )
            }
        )
    }
}
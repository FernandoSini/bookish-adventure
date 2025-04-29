package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

class BillingMenuScreen(private val navController: NavController) {


    @Composable
    fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Black,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets
        ) {
            Surface(
                Modifier.fillMaxSize().padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ), color = Color.Transparent
            ) {



            }

        }


    }
}
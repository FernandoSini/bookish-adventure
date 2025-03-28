package br.com.fernandosini.bookishadventure.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon


import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

class SearchScreen(private var navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun Content() {
        var text by remember { mutableStateOf("") }
        Scaffold(modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black,
            topBar = {
                CenterAlignedTopAppBar(
                    expandedHeight = 100.dp,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    windowInsets = WindowInsets.statusBars,
                    title = {
                        TextField(
                            maxLines = 1,
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color(0xffA3A0A0)
                                )
                            },
                            //keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            textStyle = TextStyle(textIndent = TextIndent(10.sp)),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = Color(0xff1E1E1E),
                                textColor = Color(0xffA3A0A0),
                            ),
                            value = text,
                            shape = RoundedCornerShape(20.dp),
                            onValueChange = { text = it },
                            placeholder = {
                                Text(
                                    "Search",
                                    color = Color(0xffA3A0A0),
                                    style = TextStyle(textIndent = TextIndent(10.sp))
                                )
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                                .padding(horizontal = 5.dp),

                            )

                    },

                    )
            }
        ) {


        }
    }
}


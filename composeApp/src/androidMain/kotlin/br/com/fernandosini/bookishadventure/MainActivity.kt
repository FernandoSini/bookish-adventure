package br.com.fernandosini.bookishadventure

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.fernandosini.bookishadventure.screens.App

//import dev.gitlive.firebase.Firebase
//import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = createRoomDatabase(applicationContext)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT, Color.TRANSPARENT, detectDarkMode = { darkMode ->
                    true
                }

            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                detectDarkMode = { darkMode ->
                    true
                })
        )
        // Firebase.initialize(this)
        LanguageManager(this)
        setContent {
            App(database)
        }
    }
}

/*
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}*/

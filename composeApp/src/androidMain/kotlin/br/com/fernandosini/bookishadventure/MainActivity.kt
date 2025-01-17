package br.com.fernandosini.bookishadventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.fernandosini.bookishadventure.screens.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = createRoomDatabase(applicationContext)
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

package br.com.flemis.bookishadventure.data.datasource.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.flemis.bookishadventure.data.datasource.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers


fun getDatabase(ctx: Context): AppDatabase {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("adventure-bookish.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

}

fun deleteDatabase(ctx: Context): Boolean {
    val appContext = ctx.applicationContext
    return appContext.deleteDatabase("adventure-bookish.db")
}








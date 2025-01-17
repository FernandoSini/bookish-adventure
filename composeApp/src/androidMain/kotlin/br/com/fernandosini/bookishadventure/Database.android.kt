package br.com.fernandosini.bookishadventure

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import kotlinx.coroutines.Dispatchers

fun createRoomDatabase(ctx: Context): AppDatabase {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("adventure-bookish.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ) .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}



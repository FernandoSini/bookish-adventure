package br.com.fernandosini.bookishadventure

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.fernandosini.bookishadventure.repository.db.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
//import repository.database.instantiateImpl

fun createRoomDatabase(): AppDatabase {
    val dbFilePath = documentDirectory() + "/bookish_adventure.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
   //     factory =  { AppDatabase::class.instantiateImpl() }
    ) .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
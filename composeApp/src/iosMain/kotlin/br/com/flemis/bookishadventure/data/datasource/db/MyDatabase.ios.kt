package br.com.flemis.bookishadventure.data.datasource.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.flemis.bookishadventure.data.datasource.local.db.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

//import repository.database.instantiateImpl

fun getDatabase(): AppDatabase {
    val dbFilePath = documentDirectory() + "/bookish_adventure.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        //     factory =  { AppDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO).build()

}

fun deleteDatabase(): Boolean {
    val appContext = documentDirectory()
    val dbFilePath = "$appContext/bookish_adventure.db"
    val fileManager = NSFileManager.defaultManager
    return fileManager.removeItemAtPath(dbFilePath, error = null)
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



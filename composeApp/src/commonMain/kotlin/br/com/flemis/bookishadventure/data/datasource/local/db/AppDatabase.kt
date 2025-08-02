package br.com.flemis.bookishadventure.data.datasource.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import br.com.flemis.bookishadventure.data.datasource.local.dao.UserDao
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel
import br.com.flemis.bookishadventure.utils.ConvertersRoom

@Database(entities = [UserModel::class], version = 1,)
@TypeConverters(ConvertersRoom::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    //abstract fun getTheme(): ThemeRepository
}
// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT",)
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
   override fun initialize(): AppDatabase
}


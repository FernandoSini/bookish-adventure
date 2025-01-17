package br.com.fernandosini.bookishadventure.repository.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import br.com.fernandosini.bookishadventure.models.entities.UserEntity
import br.com.fernandosini.bookishadventure.repository.dao.UserRepository
import br.com.playtips.utils.ConvertersRoom

@Database(entities = [UserEntity::class], version = 1,)
@TypeConverters(ConvertersRoom::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserRepository

    //abstract fun getTheme(): ThemeRepository
}
// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT",)
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
   override fun initialize(): AppDatabase
}


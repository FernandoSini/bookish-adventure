package br.com.flemis.bookishadventure.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel


@Dao
interface UserDao {

    @Insert
    suspend fun saveUser(user: UserModel)

    //suspend fun getUser(): UserModel

    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM Local_User where id = :userID")
    suspend fun getUserByID(userID: Long): UserModel

    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM Local_User")
    suspend fun getCurrentUser(): UserModel

}
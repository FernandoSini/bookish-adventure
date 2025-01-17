package br.com.fernandosini.bookishadventure.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fernandosini.bookishadventure.models.entities.UserEntity

@Dao
interface UserRepository {

    @Insert
    suspend fun saveUser(){

    }

    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity {
        return UserEntity(
            id = TODO(),
            firstname = TODO(),
            lastname = TODO(),
            email = TODO(),
            avatar = TODO()
        )

    }
}
package br.com.fernandosini.bookishadventure.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstname: String,
    val lastname: String,
    val email: String,
    val avatar: String,

) {

}
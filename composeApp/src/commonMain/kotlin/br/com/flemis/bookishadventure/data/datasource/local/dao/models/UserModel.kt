package br.com.flemis.bookishadventure.data.datasource.local.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SubscriptionType {
    FREE,
    PAID
}

@Entity(tableName = "Local_User")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val avatar: String,
    val password: String,
    val isVerified: Boolean
) {

}
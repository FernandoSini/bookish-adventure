package br.com.flemis.bookishadventure.core.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Long,

    @SerialName("firstname")
    val firstname: String,

    @SerialName("username")
    val username: String,
    @SerialName("lastname")
    val lastname: String,

    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,

    @SerialName("avatar")
    val avatar: String,

    @SerialName("isVerified")
    val isVerified: Boolean = false
)
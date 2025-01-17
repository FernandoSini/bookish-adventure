package br.com.playtips.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("description")
    val description: String = "",
    @SerialName("status")
    val status: String = ""
)

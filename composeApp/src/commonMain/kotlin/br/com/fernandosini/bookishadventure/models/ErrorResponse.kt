package br.com.playtips.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: Error = Error()
)
package br.com.flemis.bookishadventure.core.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock.System.now

@Serializable
data class ErrorModel(
    @SerialName("statusCode")
    val code: Int = 0,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = "",
    @SerialName("timestamp")
    val timestamp: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
)

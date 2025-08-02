package br.com.flemis.bookishadventure.core.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import br.com.flemis.bookishadventure.core.domain.models.ErrorModel


@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: ErrorModel = ErrorModel()
)
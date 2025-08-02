package br.com.flemis.bookishadventure.utils.exceptions

actual class PermissionDeniedException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {
    actual constructor(string: String, throwable: Throwable) : this(null, null) {
    }
}
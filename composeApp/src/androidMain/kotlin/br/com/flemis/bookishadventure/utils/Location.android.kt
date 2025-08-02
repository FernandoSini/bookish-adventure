package br.com.flemis.bookishadventure.utils

actual class Location {
    actual val latitude: Double
        get() = TODO("Not yet implemented")
    actual val longitude: Double
        get() = TODO("Not yet implemented")

    actual override fun toString(): String {
        TODO("Not yet implemented")
    }

    actual companion object {
        actual fun from(
            latitude: Double,
            longitude: Double
        ): Location {
            TODO("Not yet implemented")
        }

        actual fun fromString(location: String): Location {
            TODO("Not yet implemented")
        }
    }
}
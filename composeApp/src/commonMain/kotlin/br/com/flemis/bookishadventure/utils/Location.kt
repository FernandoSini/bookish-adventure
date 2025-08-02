package br.com.flemis.bookishadventure.utils

expect class Location {
    val latitude: Double
    val longitude: Double

    companion object {
        fun from(latitude: Double, longitude: Double): Location
        fun fromString(location: String): Location
    }

    override fun toString(): String
}
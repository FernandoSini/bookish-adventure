package br.com.flemis.bookishadventure.utils

expect class Location {

    companion object {
        val latitude: Double
        val longitude: Double

        fun from(latitude: Double, longitude: Double): Location
        fun fromString(location: String): Location

    }

    override fun toString(): String
}
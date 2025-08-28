package br.com.flemis.bookishadventure.features.maps.domain.entities

data class Coordinate(val latitude: Double, val longitude: Double, val zoom: Double = 1.0) {


    override fun toString(): String {
        return "Coordinate(latitude=$latitude, longitude=$longitude, zoom=$zoom)"
    }

    companion object {
        fun from(latitude: Double, longitude: Double, zoom: Double = 1.0): Coordinate {
            return Coordinate(latitude, longitude, zoom)
        }

        fun fromString(coordinate: String): Coordinate {
            val parts = coordinate.split(",")
            return Coordinate(parts[0].toDouble(), parts[1].toDouble(), parts.getOrNull(2)?.toDouble() ?: 1.0)
        }
    }
}

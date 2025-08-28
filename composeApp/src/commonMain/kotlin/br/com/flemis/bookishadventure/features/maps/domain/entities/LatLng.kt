package br.com.flemis.bookishadventure.features.maps.domain.entities

interface LatLng {
    val latitude: Double
    val longitude: Double
}

expect fun Coordinate.toLatLng(): LatLng
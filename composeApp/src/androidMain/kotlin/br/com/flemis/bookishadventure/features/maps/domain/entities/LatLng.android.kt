package br.com.flemis.bookishadventure.features.maps.domain.entities

import com.google.android.gms.maps.model.LatLng as GoogleLatLng

actual fun Coordinate.toLatLng(): LatLng = AndroidLatLng(GoogleLatLng(latitude, longitude))

class AndroidLatLng(
    val data: GoogleLatLng,
    override val latitude: Double = data.latitude,
    override val longitude: Double = data.longitude
) : LatLng {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AndroidLatLng

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}

val LatLng.data: GoogleLatLng get() = GoogleLatLng(latitude, longitude)
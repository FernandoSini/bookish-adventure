package br.com.flemis.bookishadventure.utils

import br.com.flemis.bookishadventure.utils.widget.BottomNav
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation

actual class Location {
    actual val latitude: Double = CLLocation().getLatitude()
    actual val longitude: Double = CLLocation().getLongitude()


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


@OptIn(ExperimentalForeignApi::class)
internal fun CLLocation.getLatitude(): Double = this.coordinate().useContents { latitude }
internal fun CLLocation.getLongitude(): Double = this.coordinate().useContents { longitude }



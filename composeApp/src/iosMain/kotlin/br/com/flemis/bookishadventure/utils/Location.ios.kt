package br.com.flemis.bookishadventure.utils

import br.com.flemis.bookishadventure.utils.widget.BottomNav
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.jetbrains.skiko.available
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager


actual class Location {

    internal val locationManager = CLLocationManager()

    actual companion object {
        actual val latitude: Double
        actual val longitude: Double

        init {
            latitude = CLLocationManager().getLatitude()
            longitude = CLLocationManager().getLongitude()
        }

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
internal fun CLLocationManager.getLatitude(): Double = this.location!!.coordinate().useContents { latitude }
internal fun CLLocationManager.getLongitude(): Double = this.location!!.coordinate().useContents { longitude }



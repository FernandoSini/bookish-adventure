package br.com.flemis.bookishadventure.features.maps.domain.entities

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake

@OptIn(ExperimentalForeignApi::class)
actual fun Coordinate.toLatLng(): LatLng = AppleLatLng(CLLocationCoordinate2DMake(latitude, longitude))


@OptIn(ExperimentalForeignApi::class)
internal class AppleLatLng(val data: CValue<CLLocationCoordinate2D>) : LatLng {

    override val latitude: Double = this.data.useContents { latitude }
    override val longitude: Double = this.data.useContents { longitude }
}


@OptIn(ExperimentalForeignApi::class)
internal val LatLng.data: CValue<CLLocationCoordinate2D>
    get() = (this as AppleLatLng).data
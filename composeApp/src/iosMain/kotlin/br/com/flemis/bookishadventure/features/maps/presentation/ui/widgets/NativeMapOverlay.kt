package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2D
import platform.MapKit.MKMapRect
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKOverlayRenderer
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
//aqui nessa classe  implementamos o protocolo MKOverlayProtocol, e permite customizar o overlay pelos callbacks
class NativeMapOverlay(
    private val delegate: MKOverlayProtocol,
    private val rendererInitializer: (MKOverlayProtocol) -> MKOverlayRenderer
) : NSObject(), MKOverlayProtocol {

    override fun coordinate(): CValue<CLLocationCoordinate2D> = delegate.coordinate()
    override fun boundingMapRect(): CValue<MKMapRect> = delegate.boundingMapRect()
    fun initRenderer(): MKOverlayRenderer = rendererInitializer(delegate)
}
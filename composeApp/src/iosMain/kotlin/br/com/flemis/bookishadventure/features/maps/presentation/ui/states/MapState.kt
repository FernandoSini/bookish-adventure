package br.com.flemis.bookishadventure.features.maps.presentation.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.flemis.bookishadventure.features.maps.domain.entities.AppleLatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.Marker
import br.com.flemis.bookishadventure.features.maps.presentation.pages.MapsView
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.NativeMapAnnotation
import br.com.flemis.bookishadventure.utils.constants.FULL_ROTATION_DEGREES
import br.com.flemis.bookishadventure.utils.constants.TILE_SIZE
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.collections.immutable.ImmutableList
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpan
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKMapPointForCoordinate
import platform.MapKit.MKMapRect
import platform.MapKit.MKMapRectMake
import platform.MapKit.MKMapRectUnion
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation
import platform.UIKit.UIEdgeInsetsMake
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.round


class AppleMapState() : MapState {
    override var zoom: Float by mutableStateOf(15f)
    private var mapView: MKMapView? by mutableStateOf(null)
    override fun moveToCoordinates(
        coordinates: ImmutableList<LatLng>,
        padding: Int
    ) {
        val padding = padding.toDouble()
        val edgeInsets = UIEdgeInsetsMake(padding, padding, padding, padding)
        val boundingMapRect = coordinates.map {
            MKMapPointForCoordinate(CLLocationCoordinate2DMake(it.latitude, it.longitude))
        }.map { point ->
            point.useContents { MKMapRectMake(this.x, this.y, 0.0, 0.0) }
        }.reduce { acc, rect -> MKMapRectUnion(acc, rect) }

        mapView?.setVisibleMapRect(boundingMapRect, edgePadding = edgeInsets, animated = true)
    }

    override fun zoomIn() {
        val map = mapView ?: return
        val currentZoom = map.getZoomLevel() + 1
        map.setZoomLevel(currentZoom)
    }

    override fun zoomOut() {
        val map = mapView ?: return
        val currentZoom = map.getZoomLevel() - 1
        map.setZoomLevel(currentZoom)
    }

    override fun addMarker(marker: Marker) {
        val map = mapView ?: return
        val currentAnnotation = MKPointAnnotation(
            CLLocationCoordinate2DMake(
                latitude = marker.coordinate.latitude,
                longitude = marker.coordinate.longitude
            ),
            title = "teste",
            subtitle = "subtitle"
        )
     

    }

    override fun removeMarker(marker: Marker) {

    }

    fun setMap(newMapView: MKMapView?) {
        mapView = newMapView
    }

    fun onUpdated() {
        val map = mapView ?: return
        zoom = map.getZoomLevel()
    }
}

fun MapState.setMap(mkMapView: MKMapView?) {
    (this as AppleMapState).setMap(mkMapView)
}

fun MapState.onMapUpdate() {
    (this as AppleMapState).onUpdated()
}


@Composable
actual fun rememberMapState(): MapState = remember { AppleMapState() }

@OptIn(ExperimentalForeignApi::class)
private fun MKMapView.getZoomLevel(): Float {

    val width = frame.useContents { size.width }
    val region = region.useContents { span.longitudeDelta }
    return round(log2(FULL_ROTATION_DEGREES * (width / TILE_SIZE) / region)).toFloat()

}

@OptIn(ExperimentalForeignApi::class)
private fun MKMapView.setZoomLevel(zoomLevel: Float) = moveToCoordinate(centerCoordinate, zoomLevel)


@OptIn(ExperimentalForeignApi::class)
private fun MKMapView.moveToCoordinate(coordinate: CValue<CLLocationCoordinate2D>, zoomLevel: Float) {
    val width = frame.useContents { size.width }

    val span = MKCoordinateSpanMake(
        latitudeDelta = 0.0,
        longitudeDelta = FULL_ROTATION_DEGREES / 2.0.pow(zoomLevel.toDouble()) * (width / TILE_SIZE)
    )

    setRegion(MKCoordinateRegionMake(coordinate, span), animated = true)
}
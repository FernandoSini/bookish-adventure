package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import io.github.aakira.napier.Napier
import platform.MapKit.MKMapItem
import platform.MapKit.MKMapView
import platform.MapKit.overlays
import platform.MapKit.removeOverlays
import platform.UIKit.UIColor


@Composable
fun rememberMkMapView(): MKMapView {

    val mapView = remember { MKMapView() }
    DisposableEffect(mapView) {
        onDispose {
            Napier.d { "MapView onDispose" }
            mapView.setDelegate(null)
            mapView.removeAnnotations(mapView.annotations)
            mapView.removeOverlays(mapView.overlays)
            mapView.removeFromSuperview()

        }

    }
    return mapView
}

val LocalMapView = staticCompositionLocalOf<MKMapView> {
    error("MapView not initialized")
}

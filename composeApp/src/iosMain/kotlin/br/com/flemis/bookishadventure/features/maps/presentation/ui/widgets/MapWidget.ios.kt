package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.viewinterop.UIKitViewController
import br.com.flemis.bookishadventure.LocalNativeViewFactory
import br.com.flemis.bookishadventure.NativeViewFactory
import br.com.flemis.bookishadventure.utils.Location
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapType
import platform.MapKit.MKMapTypeHybridFlyover
import platform.MapKit.MKMapView
//esse era um teste que eu estava fazendo mas que sera descartado futuramente
@Composable
actual fun NativeMapWidget(
    latitude: Double,
    longitude: Double,
    zoom: Double,
    //onMapReady: () -> Unit,
    onMapDisposed: () -> Unit,
    modifier: Modifier
) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = modifier,
        update = { viewController ->
            // Update the view controller with the new latitude, longitude, and zoom
            factory.updateMapView(
                viewController = viewController,
                newLatitude = latitude,
                newLongitude = longitude,
                newZoom = zoom
            )
        },
        onRelease = {
            onMapDisposed.invoke()
            factory.releaseMapView(it)

        },
        onReset = { it -> },
        factory = {
            factory.createSwiftMap(latitude, longitude, zoom)
        }
    )
}



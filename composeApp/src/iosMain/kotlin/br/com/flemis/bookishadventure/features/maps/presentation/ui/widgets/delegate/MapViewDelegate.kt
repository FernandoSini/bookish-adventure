package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.delegate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.NativeMapAnnotation
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.NativeMapOverlay
import kotlinx.cinterop.ObjCSignatureOverride
import platform.MapKit.MKAnnotationProtocol
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKOverlayRenderer
import platform.MapKit.MKPolyline
import platform.MapKit.MKPolylineRenderer
import platform.UIKit.UIColor
import platform.UIKit.systemBlueColor
import platform.darwin.NSObject


@Composable
fun rememberMkMapViewDelegate(onMapVisibleRegionChanged: (MKMapView) -> Unit = {}): MKMapViewDelegateProtocol {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val currentOnMapVisibleRegionChanged by rememberUpdatedState(onMapVisibleRegionChanged)

    return remember(density, layoutDirection, currentOnMapVisibleRegionChanged) {
        object : NSObject(), MKMapViewDelegateProtocol {

            override fun mapViewDidChangeVisibleRegion(mapView: MKMapView) = currentOnMapVisibleRegionChanged(mapView)

            @ObjCSignatureOverride
            override fun mapView(mapView: MKMapView, rendererForOverlay: MKOverlayProtocol): MKOverlayRenderer {
                val overlay = rendererForOverlay as? NativeMapOverlay ?: return MKOverlayRenderer(rendererForOverlay)

                return overlay.initRenderer()
            }

            //aqui usamos recycling views, ou seja, se o view ja foi criado, ele reutiliza o view, se no tem view, ele cria um novo.
            //a view é atualizada com os dados da annotation, que é chamado quando a view é criada ou reutilizada
            @ObjCSignatureOverride
            override fun mapView(mapView: MKMapView, viewForAnnotation: MKAnnotationProtocol): MKAnnotationView? {
                val annotation = viewForAnnotation as? NativeMapAnnotation ?: return null
                var annotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(annotation.identifier)

                if (annotationView == null) {
                    annotationView = annotation.createView()
                } else {
                    annotationView.annotation = viewForAnnotation
                }

                annotation.bindView(annotationView)
                return annotationView
            }


        }
    }
}

/*
@Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@ObjCSignatureOverride
override fun mapView(mapsView: MKMapView, rendererForOverlay: MKOverlayProtocol): MKOverlayRenderer {
    val polyline = rendererForOverlay as? MKPolyline ?: return MKOverlayRenderer()

    val renderer = MKPolylineRenderer(overlay = polyline).apply {
        setStrokeColor(UIColor.systemBlueColor)
        setLineWidth(3.0)
    }
    return renderer;

}*/

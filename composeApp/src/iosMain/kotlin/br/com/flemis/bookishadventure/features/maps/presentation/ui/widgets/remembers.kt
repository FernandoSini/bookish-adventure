package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import br.com.flemis.bookishadventure.features.maps.domain.entities.Coordinate
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.Marker
import br.com.flemis.bookishadventure.features.maps.domain.entities.data
import br.com.flemis.bookishadventure.utils.toUIColor
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.interpretPointed
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.sizeOf
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKPointAnnotation
import kotlinx.collections.immutable.ImmutableList
import platform.CoreGraphics.CGLineCap
import platform.CoreLocation.CLLocationCoordinate2D
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKPolyline
import platform.MapKit.MKPolylineRenderer


@Composable
fun rememberMarkAnnotation(marker: Marker): MKPointAnnotation {
    return remember(marker) {
        val clLocation =
            CLLocationCoordinate2DMake(
                marker.coordinate.latitude,
                marker.coordinate.longitude,

                )

        MKPointAnnotation().apply {
            setTitle(marker.title)
            setCoordinate(clLocation)
        }
    }

}

// entao aqui precisamos alocar um array do tipo C fixo qe controle as coordenadas para fazer a parte nativa do mapkit
@Composable
fun rememberPolylineOverlay(points: ImmutableList<LatLng>, color: Color, width: Dp): MKOverlayProtocol {
    val polyline= remember(points) {
        val locations = points.map{it.data}
        memScoped { // criando um contexto seguro para alocacao de memorya de recursos nativos que libera automaticamente os recursos ao fim do processo
            val coordinates = allocArray<CLLocationCoordinate2D>(points.size)
            // aqui estamos transformando o array de cordenadas para array de C que é do nativo para garantimos a posicao de memoria correta
            locations.forEachIndexed { index, location ->

                val offset = index * sizeOf<CLLocationCoordinate2D>()
                val pointer = coordinates.rawValue + offset
                location.place(interpretPointed<CLLocationCoordinate2D>(pointer).ptr)
            }

            MKPolyline.polylineWithCoordinates(
                coords = coordinates,
                count = locations.size.toULong()
            )
        }
    }
    return remember(points, color, width){
        NativeMapOverlay(
            delegate = polyline,
            rendererInitializer = {
                MKPolylineRenderer(overlay = polyline).apply{
                    setStrokeColor(color.toUIColor())
                    setLineWidth(width.value.toDouble())
                    setLineCap(CGLineCap.kCGLineCapRound)
                }
            }
        )
    }

}

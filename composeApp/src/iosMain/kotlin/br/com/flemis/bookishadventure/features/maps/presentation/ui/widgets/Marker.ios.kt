package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.utils.toUIImage
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGPointMake
import platform.MapKit.MKAnnotationView
import platform.MapKit.addOverlay
import platform.MapKit.removeOverlay
import platform.UIKit.UIImage
import platform.UIKit.UIImageView

@Composable
@NativeMapComposable
actual fun PolylineWidget(
    points: ImmutableList<LatLng>,
    color: Color,
    width: Dp
) {
    val mapView = LocalMapView.current
    val overlay = rememberPolylineOverlay(points, color, width)
    //aqui controlamos o ciclo de vida do overlay
    DisposableEffect(mapView, points, color, width) {
        mapView.addOverlay(overlay)
        onDispose {
            Napier.d {"Polyline Disposed" }
            mapView.removeOverlay(overlay)
        }
    }
}

//aqui temos que converter o bitmap para UIImage, estamos convertendo o conteudo do Compose UI para imagems que o MapKit entende
@Composable
@NativeMapComposable
actual fun MarkerInfoWidget(
    vararg keys: Any,
    position: LatLng,
    anchor: Offset,
    infoContent: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)
) {
    val density = LocalDensity.current
    val mapView = LocalMapView.current

    var annotation by remember { mutableStateOf<NativeMapAnnotation?>(null) }

    val contentGraphicsLayer = rememberGraphicsLayer()
    val currentContent by rememberUpdatedState(content)

    val infoContentGraphicsLayer = rememberGraphicsLayer()
    val currentInfoContent by rememberUpdatedState(infoContent)

    Box(modifier = Modifier.drawWithContent {
        contentGraphicsLayer.record {
            this@drawWithContent.drawContent()
        }
    }) {
        currentContent()
    }

    if (currentInfoContent != null) {
        Box(modifier = Modifier.drawWithContent {
            infoContentGraphicsLayer.record {
                this@drawWithContent.drawContent()
            }
        }) {
            currentInfoContent?.invoke()
        }

    }
    LaunchedEffect(*keys, currentContent, currentInfoContent) {
        val uiImage = withContext(Dispatchers.Main) {
            contentGraphicsLayer.toImageBitmap().toUIImage(
                density.density,
                // alpha = contentGraphicsLayer.alpha,
                //colorFilter = contentGraphicsLayer.colorFilter
            )
        }
        val infoImage = withContext(Dispatchers.Main) {
            if (infoContent != null) {
                infoContentGraphicsLayer.toImageBitmap().toUIImage(
                    density.density,
                    //     alpha = infoContentGraphicsLayer.alpha,
                    //   colorFilter = infoContentGraphicsLayer.colorFilter
                )

            } else {
                null
            }
        }

        annotation?.let {
            mapView.removeAnnotation(it)
        }

        val reuseIdentifier = keys.joinToString()
        annotation = NativeMapAnnotation(
            identifier = reuseIdentifier,
            position = position,
            viewCreator = { MKAnnotationView(it, reuseIdentifier) },
            viewBinder = { convertView ->
                viewBinder(
                    convertView,
                    uiImage,
                    infoImage,
                    anchor
                )
            }
        ).also { mapView.addAnnotation(it) }
    }
    DisposableEffect(Unit) {
        onDispose {
            annotation?.let {
                mapView.removeAnnotation(it)
            }
            annotation = null
        }
    }

}

@Composable
@NativeMapComposable
actual fun MarkerWidget(
    vararg keys: Any,
    position: LatLng,
    anchor: Offset,
    infoContent: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)
) {
    MarkerInfoWidget(keys = keys, position = position, anchor = anchor, infoContent = infoContent, content = content)
}


@OptIn(ExperimentalForeignApi::class)
private fun viewBinder(
    convertView: MKAnnotationView,
    image: UIImage?,
    infoImage: UIImage?,
    anchor: Offset
) {
    convertView.image = image
    if (infoImage != null) {
        convertView.canShowCallout = true
        val infoImageView = UIImageView(infoImage)
        convertView.detailCalloutAccessoryView = infoImageView
    } else {
        convertView.canShowCallout = false
        convertView.detailCalloutAccessoryView = null
    }
    //ajustando o anchor point para o ponto correto ainda n funfa por causa do width and height
    convertView.centerOffset = CGPointMake(
        x = anchor.x.toDouble(),
        y = anchor.y.toDouble()
    )
}

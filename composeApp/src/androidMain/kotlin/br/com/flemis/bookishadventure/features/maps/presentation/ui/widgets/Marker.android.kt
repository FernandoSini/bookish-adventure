package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.data

import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindowComposable
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.collections.immutable.ImmutableList
import com.google.maps.android.compose.Polyline as GooglePolyline

/*
@Target(
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER
)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual annotation class NativeMapComposable
*/

@Composable
@NativeMapComposable
actual fun PolylineWidget(
    points: ImmutableList<LatLng>,
    color: Color,
    width: Dp
) {
    val googleLatLngList = remember(points) { points.map { it.data } }
    GooglePolyline(
        points = googleLatLngList,
        color = color,
        width = with(LocalDensity.current) { width.toPx() },
        endCap = RoundCap(),
        startCap = RoundCap(),
    )
}

@Composable
@NativeMapComposable
actual fun MarkerInfoWidget(
    vararg keys: Any,
    position: LatLng,
    anchor: Offset,
    infoContent: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)
) {
    MarkerInfoWindowComposable(
        keys = keys,
        state = rememberMarkerState(position = position.data, key = null),
        anchor = anchor,
        content = content,
        infoContent = infoContent?.let { { infoContent() } }

    )

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
    MarkerComposable(
        keys = keys,
        state = rememberMarkerState(position = position.data, key = null),
        anchor = anchor,
        content = content,

        )
}
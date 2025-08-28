package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import kotlinx.collections.immutable.ImmutableList

@Composable
@NativeMapComposable
expect fun MarkerWidget(
    vararg keys: Any,
    position: LatLng,
    anchor: Offset = Offset(0.5f, 1.5f),
    infoContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
)

@Composable
@NativeMapComposable
expect fun MarkerInfoWidget(
    vararg keys: Any,
    position: LatLng,
    anchor: Offset = Offset(0.5f, 1.5f),
    infoContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
)

@Composable
@NativeMapComposable
expect fun PolylineWidget(
    points: ImmutableList<LatLng>,
    color: Color = Color.Black,
    width: Dp = 2.dp
)
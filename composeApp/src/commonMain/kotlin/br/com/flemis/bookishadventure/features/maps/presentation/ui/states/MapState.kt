package br.com.flemis.bookishadventure.features.maps.presentation.ui.states

import androidx.compose.runtime.Composable
import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.Marker
import kotlinx.collections.immutable.ImmutableList

interface MapState {

    val zoom: Float

    val isZoomLimit: Boolean
        get() = zoom >= 15f

    fun moveToCoordinates(coordinates: ImmutableList<LatLng>, padding: Int)

    fun zoomIn()
    fun zoomOut()

    fun addMarker(marker: Marker)
    fun removeMarker(marker: Marker)

}
@Composable
expect fun rememberMapState(): MapState
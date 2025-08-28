package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeMapWidget(
    latitude: Double,
    longitude: Double,
    zoom: Double,
    //   onMapReady: () -> Unit,
    onMapDisposed: () -> Unit,
    modifier: Modifier
) {
}
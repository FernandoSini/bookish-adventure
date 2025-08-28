package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.MapState
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.setCameraState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@Target(allowedTargets = [AnnotationTarget.FILE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.TYPE, AnnotationTarget.TYPE_PARAMETER])
actual annotation class NativeMapComposable actual constructor()

@Composable
actual fun NativeMapWidgetV2(
    modifier: Modifier,
    contentPadding: PaddingValues,
    mapState: MapState,

    content: @Composable @NativeMapComposable (() -> Unit)

) {

    val cameraPositionState = rememberCameraPositionState()
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = true,
            mapToolbarEnabled = true,
            tiltGesturesEnabled = true,
            zoomControlsEnabled = true,
            zoomGesturesEnabled = true,
            scrollGesturesEnabled = true,
            myLocationButtonEnabled = true,
            indoorLevelPickerEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,
            rotationGesturesEnabled = true,
        ),
        contentPadding = contentPadding,
        content = content,
    )

    DisposableEffect(mapState, cameraPositionState) {
        mapState.setCameraState(cameraPositionState)
        onDispose {
            mapState.setCameraState(null)
        }
    }
}
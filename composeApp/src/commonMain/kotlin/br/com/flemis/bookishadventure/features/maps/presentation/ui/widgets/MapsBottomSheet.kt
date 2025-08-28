package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun MapsBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: ModalBottomSheetState,
    screenContent: @Composable (() -> Unit),
    sheetContentColor: Color = MaterialTheme.colorScheme.secondary,
    sheetBackgroundColor: Color,
    sheetShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetGesturesEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    isDisposed: Boolean = false,
) {
    DisposableEffect(isDisposed, sheetContent) {
        onDispose {

            Napier.d { "MapsBottomSheet onDispose"  }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        sheetState = sheetState,
        content = screenContent,
        sheetContentColor = sheetContentColor,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetShape = sheetShape,
        sheetGesturesEnabled = sheetGesturesEnabled,
        scrimColor = scrimColor,
        modifier = modifier,
        sheetElevation = sheetElevation,
    )
}
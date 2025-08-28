package br.com.flemis.bookishadventure.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import platform.UIKit.UIImage


fun Painter.toUIImage(density: Density, layoutDirection: LayoutDirection,size: Size = intrinsicSize, scale:Float = 1f): UIImage {
    return toImageBitmap(density, layoutDirection,size).toUIImage(scale)
}

suspend fun GraphicsLayer.toUIImage(scale:Float = 1f): UIImage {
    return toImageBitmap().toUIImage(scale)
}
package br.com.flemis.bookishadventure.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


fun Painter.toImageBitmap(density: Density, layoutDirection: LayoutDirection, size: Size): ImageBitmap {
    val image = ImageBitmap(size.width.toInt(), size.height.toInt(), config = ImageBitmapConfig.Argb8888)

    val canvas = Canvas(image)
    CanvasDrawScope().draw(density, layoutDirection, canvas, size) {
        draw(size)
    }
    return image
}
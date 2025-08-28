package br.com.flemis.bookishadventure.utils

import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIColor


@OptIn(ExperimentalForeignApi::class)
internal fun Color.toUIColor(): UIColor= UIColor(
    red = red.toDouble(),
    green = green.toDouble(),
    blue = blue.toDouble(),
    alpha = alpha.toDouble()

)
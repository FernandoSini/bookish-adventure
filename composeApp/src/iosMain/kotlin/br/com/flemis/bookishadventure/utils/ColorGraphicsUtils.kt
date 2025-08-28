package br.com.flemis.bookishadventure.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGColorSpaceRef
import platform.CoreGraphics.CGColorSpaceRelease
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGContextRelease

import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGImageRelease
import platform.CoreGraphics.kCGImageByteOrder32Little
import platform.UIKit.UIImage
import platform.UIKit.UIImageOrientation


@OptIn(ExperimentalForeignApi::class)
internal fun ImageBitmap.toCGImage(): CGImageRef? = withCFReleaseScope {
    if (config != ImageBitmapConfig.Argb8888) {
        throw IllegalArgumentException("ImageBitmap must be in ARGB_8888 format to convert to CGImageRef")
    }

    val buffer = IntArray(width * height)

    //vamos ler os pixels da imagem
    readPixels(buffer)

    val colorSpace = CGColorSpaceCreateDeviceRGB()?.deferRelease()

    val bitmapInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value or kCGImageByteOrder32Little

    val context = CGBitmapContextCreate(
        buffer.refTo(0),
        width.toULong(),
        height.toULong(),
        8u,
        (width * 4).toULong(),
        colorSpace,
        bitmapInfo
    )?.deferRelease()

    CGBitmapContextCreateImage(context)


}

@OptIn(ExperimentalForeignApi::class)
internal fun ImageBitmap.toUIImage(scale: Float = 1.0f): UIImage = withCFReleaseScope {
    val cgImage = toCGImage()?.deferRelease() ?: error("Failed to convert ImageBitmap to CGImageRef")
    UIImage.imageWithCGImage(cgImage, scale.toDouble(), UIImageOrientation.UIImageOrientationUp)
}

@OptIn(ExperimentalForeignApi::class)
private sealed interface CFScopeReleasable {
    fun release()

    data class Image(val image: CGImageRef) : CFScopeReleasable {
        override fun release() = CGImageRelease(image)
    }

    data class ColorSpace(val colorSpace: CGColorSpaceRef) : CFScopeReleasable {
        override fun release() = CGColorSpaceRelease(colorSpace)
    }

    data class Context(val context: CGContextRef) : CFScopeReleasable {
        override fun release() = CGContextRelease(context)
    }
}

@OptIn(ExperimentalForeignApi::class)
private class CFReleaseScope {
    private val items = mutableListOf<CFScopeReleasable>()

    fun release() = items.reversed().forEach { it.release() }

    private fun add(item: CFScopeReleasable) = items.add(item)

    fun CGImageRef.deferRelease(): CGImageRef = this.also { add(CFScopeReleasable.Image(it)) }
    fun CGColorSpaceRef.deferRelease(): CGColorSpaceRef = this.also { CFScopeReleasable.ColorSpace(this) }
    fun CGContextRef.deferRelease(): CGContextRef = this.also { CFScopeReleasable.Context(this) }
}


private fun <R> withCFReleaseScope(block: CFReleaseScope.() -> R): R {
    val scope = CFReleaseScope()
    return try {
        block(scope)
    } finally {
        scope.release()
    }
}
package com.tamaki.workerapp.ui.theme.customShapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleShapeRounded(private val cornerRadius: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTriangleRoundedPath(size = size,cornerRadius = cornerRadius)
        )
    }
}

fun drawTriangleRoundedPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()

        lineTo(x = size.width , y = size.height)
        lineTo(x = cornerRadius, y = size.height)
        arcTo(
            rect = Rect(
                left = 0f,
                top = size.height - cornerRadius*2f,
                right = cornerRadius*2f,
                bottom = size.height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )
        lineTo(x = 0f, y = 0f)
    }
}
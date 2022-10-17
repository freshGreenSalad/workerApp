package com.tamaki.workerapp.ui.theme.customShapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class WorkerCardShape(private val cornerRadius: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTicketPath(size = size, cornerRadius = cornerRadius)
        )
    }
}


fun drawTicketPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                left = 0f,
                top = 0f,
                right = cornerRadius*2f,
                bottom = cornerRadius*2f
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width - size.width/4f, y = 0f)
        // Top right line
        lineTo(x = size.width, y = size.width/4f)
        lineTo(x = size.width, y = size.height - cornerRadius)
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius*2f,
                top = size.height - cornerRadius*2f,
                right = size.width,
                bottom = size.height
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(x = cornerRadius, y = size.height)
        // Bottom left arc
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
        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}
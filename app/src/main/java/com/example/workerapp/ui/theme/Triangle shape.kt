package com.example.workerapp.ui.theme

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleShape() : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTrianglePath(size = size,)
        )
    }
}

fun drawTrianglePath(size: Size): Path {
    return Path().apply {
        reset()

        lineTo(x = size.width , y = 0f)
        lineTo(x = size.width, y = size.height)
        lineTo(x = 0f, y = 0f)
    }
}
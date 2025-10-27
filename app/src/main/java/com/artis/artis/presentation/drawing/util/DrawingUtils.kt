package com.artis.artis.presentation.drawing.util

import android.graphics.PointF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt
import android.graphics.Color as AndroidColor

object DrawingUtils {

    fun getColorFromWheel(
        point: PointF,
        center: Offset,
        radius: Float,
        value: Float = 1f // Add value parameter
    ): Color? {
        val x = point.x
        val y = point.y

        val distance = sqrt(x * x + y * y)

        if (distance > radius) return null

        val angle = (atan2(y.toDouble(), x.toDouble()) * 180 / Math.PI).toFloat()
        val hue = (angle + 360) % 360 // Ensure hue is positive

        val saturation = min(distance / radius, 1f)

        // Use the passed value for the HSV conversion
        return Color(AndroidColor.HSVToColor(floatArrayOf(hue, saturation, value)))
    }
}
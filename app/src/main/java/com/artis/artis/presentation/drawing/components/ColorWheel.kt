package com.artis.artis.presentation.drawing.components

import android.graphics.PointF
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.artis.artis.presentation.drawing.util.DrawingUtils.getColorFromWheel
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import android.graphics.Color as AndroidColor

@Composable
fun ColorWheel(
    modifier: Modifier = Modifier,
    onColorChange: (Color) -> Unit,
    initialColor: Color = Color.Red,
    // The currentValue parameter is still here but will now always be 1f when drawing the wheel
    currentValue: Float = 1f
) {
    var selectedPoint by remember { mutableStateOf(PointF(0f, 0f)) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var radius by remember { mutableStateOf(0f) }

    // Initialize selectedPoint based on initialColor when size/radius is known
    // The initial color for positioning the selector should still respect its original HSV values
    LaunchedEffect(initialColor, radius) { // Removed currentValue from LaunchedEffect dependency
        if (radius > 0f) { // Ensure radius is set before calculating
            val hsv = FloatArray(3)
            AndroidColor.colorToHSV(initialColor.toArgb(), hsv)
            val hue = hsv[0]
            val saturation = hsv[1]

            val angleRad = Math.toRadians(hue.toDouble())
            val x = (radius * saturation * cos(angleRad)).toFloat()
            val y = (radius * saturation * sin(angleRad)).toFloat()
            selectedPoint = PointF(x, y)
        }
    }


    Canvas(
        modifier = modifier
            .onSizeChanged { size ->
                center = Offset(size.width / 2f, size.height / 2f)
                radius = minOf(size.width, size.height) / 2f
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val point = PointF(offset.x - center.x, offset.y - center.y)
                        selectedPoint = point
                        // When reporting color change from wheel, ALWAYS use V=1f
                        val color = getColorFromWheel(point, center, radius, 1f)
                        if (color != null) onColorChange(color)
                    },
                    onDrag = { change, _ ->
                        val offset = change.position
                        val point = PointF(offset.x - center.x, offset.y - center.y)
                        selectedPoint = point
                        // When reporting color change from wheel, ALWAYS use V=1f
                        val color = getColorFromWheel(point, center, radius, 1f)
                        if (color != null) onColorChange(color)
                    },
                    onDragEnd = {
                        // When reporting color change from wheel, ALWAYS use V=1f
                        val color = getColorFromWheel(selectedPoint, center, radius, 1f)
                        if (color != null) onColorChange(color)
                    }
                )
            }
    ) {
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                style = android.graphics.Paint.Style.FILL
            }

            // --- Draw Hue Ring (Always with Value = 1f) ---
            val hueColors = IntArray(361) { i ->
                AndroidColor.HSVToColor(floatArrayOf(i.toFloat(), 1f, 1f)) // Always V=1f here
            }
            val sweepGradient = SweepGradient(center.x, center.y, hueColors, null)
            paint.shader = sweepGradient
            canvas.nativeCanvas.drawCircle(center.x, center.y, radius, paint)

            // --- Draw Saturation Overlay (White to Transparent) ---
            val saturationColors = intArrayOf(
                AndroidColor.WHITE,
                AndroidColor.TRANSPARENT
            )
            val radialGradient = RadialGradient(center.x, center.y, radius, saturationColors, null, Shader.TileMode.CLAMP)
            paint.shader = radialGradient
            canvas.nativeCanvas.drawCircle(center.x, center.y, radius, paint)
        }

        // Draw selection indicator
        if (radius > 0) {
            val distance = kotlin.math.sqrt((selectedPoint.x * selectedPoint.x + selectedPoint.y * selectedPoint.y).toDouble()).toFloat()
            val clampedPoint = if (distance > radius) {
                val ratio = radius / distance
                PointF(selectedPoint.x * ratio, selectedPoint.y * ratio)
            } else {
                selectedPoint
            }

            val indicatorOffset = Offset(center.x + clampedPoint.x, center.y + clampedPoint.y)
            drawCircle(
                color = Color.White,
                radius = 10.dp.toPx(),
                center = indicatorOffset,
                style = ComposeStroke(width = 2.dp.toPx())
            )
            drawCircle(
                // The indicator on the wheel should also reflect only H and S, with V=1f
                color = getColorFromWheel(clampedPoint, center, radius, 1f) ?: Color.White,
                radius = 7.dp.toPx(),
                center = indicatorOffset
            )
        }
    }
}
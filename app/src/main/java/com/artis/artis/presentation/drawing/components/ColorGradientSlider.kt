package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import android.graphics.Color as AndroidColor

@Composable
fun ColorGradientSlider(
    value: Float, // 0f to 1f
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    hue: Float,
    saturation: Float
) {
    val localDensity = LocalDensity.current
    var sliderWidthPx by remember { mutableStateOf(0f) }
    val thumbRadius = 12.dp

    // Calculate the start and end colors for the gradient based on current hue and saturation
    val startColor = remember(hue, saturation) {
        Color(AndroidColor.HSVToColor(floatArrayOf(hue, saturation, 0f))) // Darkest (V=0)
    }
    val endColor = remember(hue, saturation) {
        Color(AndroidColor.HSVToColor(floatArrayOf(hue, saturation, 1f))) // Brightest (V=1)
    }

    // Calculate the color of the thumb
    val thumbColor = remember(value, hue, saturation) {
        Color(AndroidColor.HSVToColor(floatArrayOf(hue, saturation, value)))
    }

    Box(
        modifier = modifier
            .onSizeChanged { size ->
                sliderWidthPx = size.width.toFloat()
            }
            .clip(RoundedCornerShape(thumbRadius)) // Clip the track to rounded corners
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(startColor, endColor),
                    start = Offset(0f, 0f),
                    end = Offset(sliderWidthPx, 0f)
                )
            )
            .draggable(
                state = rememberDraggableState { delta ->
                    val newValue = (value + (delta / sliderWidthPx)).coerceIn(0f, 1f)
                    onValueChange(newValue)
                },
                orientation = Orientation.Horizontal,
            )
    ) {
        // Thumb
        Surface(
            modifier = Modifier
                .offset {
                    val x = (value * sliderWidthPx - with(localDensity) { thumbRadius.toPx() }).roundToInt()
                    IntOffset(x.coerceIn(0, (sliderWidthPx - with(localDensity) { thumbRadius.toPx() * 2 }).roundToInt()), 0)
                }
                .size(thumbRadius * 2)
                .align(Alignment.CenterStart),
            shape = CircleShape,
            color = thumbColor,
            shadowElevation = 4.dp
        ) {
            // Inner circle for the white border effect
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.White,
                    radius = (thumbRadius - 2.dp).toPx(), // Smaller radius for inner circle
                    style = Stroke(width = 2.dp.toPx()) // White border
                )
            }
        }
    }
}
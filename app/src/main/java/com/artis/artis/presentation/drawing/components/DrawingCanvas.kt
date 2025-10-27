package com.artis.artis.presentation.drawing.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.artis.artis.domain.drawing.model.Stroke

@Composable
fun DrawingCanvas(
    strokes: List<Stroke>,
    getCurrentDrawingColor: () -> Color, // Still needed for live path preview
    currentStrokeWidth: () -> Float, // Still needed for live path preview
    currentStrokeAlpha: () -> Float, // New: for live path preview
    canvasBackgroundColor: Color,
    onAddStroke: (Path) -> Unit, // Modified to take Path directly
    modifier: Modifier = Modifier
) {
    val livePathPoints = remember { mutableStateListOf<Offset>() }
    val currentLivePath = remember { Path() } // Path for the stroke currently being drawn

    // Animate canvas background color changes
    val animatedCanvasBackgroundColor by animateColorAsState(
        targetValue = canvasBackgroundColor,
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(animatedCanvasBackgroundColor)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        livePathPoints.clear()
                        currentLivePath.reset() // Reset the live path
                        livePathPoints.add(offset)
                        currentLivePath.moveTo(offset.x, offset.y) // Move to start for live path
                    },
                    onDrag = { change, _ ->
                        livePathPoints.add(change.position)
                        val p1 = livePathPoints[livePathPoints.size - 2]
                        val p2 = livePathPoints.last()
                        currentLivePath.quadraticBezierTo(
                            p1.x, p1.y,
                            (p1.x + p2.x) / 2, (p1.y + p2.y) / 2
                        )
                    },
                    onDragEnd = {
                        if (livePathPoints.isNotEmpty()) {
                            val finalPath = Path()
                            if (livePathPoints.size == 1) {
                                val point = livePathPoints.first()
                                finalPath.addArc(
                                    Rect(
                                        left = point.x - currentStrokeWidth() / 2,
                                        top = point.y - currentStrokeWidth() / 2,
                                        right = point.x + currentStrokeWidth() / 2,
                                        bottom = point.y + currentStrokeWidth() / 2
                                    ),
                                    0f,
                                    360f
                                )
                            } else {
                                finalPath.moveTo(livePathPoints.first().x, livePathPoints.first().y)
                                for (i in 1 until livePathPoints.size) {
                                    val p1 = livePathPoints[i - 1]
                                    val p2 = livePathPoints[i]
                                    finalPath.quadraticBezierTo(
                                        p1.x, p1.y,
                                        (p1.x + p2.x) / 2, (p1.y + p2.y) / 2
                                    )
                                }
                            }
                            onAddStroke(finalPath) // Pass the path directly
                            livePathPoints.clear()
                            currentLivePath.reset() // Reset for next stroke
                        }
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            strokes.forEach { stroke ->
                // Use stroke.color and stroke.alpha directly
                val displayColor = if (stroke.isEraser) canvasBackgroundColor else stroke.color
                drawPath(
                    path = stroke.path,
                    color = displayColor.copy(alpha = stroke.alpha), // Apply alpha here
                    style = ComposeStroke(
                        width = stroke.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // Draw the current live path
            if (livePathPoints.isNotEmpty()) {
                // Determine live path color and alpha based on current settings
                val livePathColor = getCurrentDrawingColor()
                val livePathAlpha = currentStrokeAlpha()

                drawPath(
                    path = currentLivePath,
                    color = livePathColor.copy(alpha = livePathAlpha),
                    style = ComposeStroke(
                        width = currentStrokeWidth(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewDrawingCanvas() {
    MaterialTheme {
        val sampleStrokes = remember {
            val path1 = Path().apply {
                moveTo(50f, 50f)
                lineTo(100f, 150f)
                quadraticBezierTo(150f, 250f, 200f, 100f)
            }
            val path2 = Path().apply {
                moveTo(250f, 100f)
                arcTo(Rect(200f, 50f, 300f, 150f), 0f, 180f, false)
            }
            listOf(
                Stroke(path1, Color.Blue, 8f),
                Stroke(path2, Color.Magenta, 12f)
            )
        }

        DrawingCanvas(
            strokes = sampleStrokes,
            getCurrentDrawingColor = { Color.Green },
            currentStrokeWidth = { 10f },
            currentStrokeAlpha = { 1f }, // Added for preview
            canvasBackgroundColor = Color.LightGray,
            onAddStroke = {}
        )
    }
}
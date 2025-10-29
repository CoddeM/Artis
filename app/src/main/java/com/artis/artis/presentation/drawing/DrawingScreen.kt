package com.artis.artis.presentation.drawing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artis.artis.presentation.drawing.components.DrawingBottomActions
import com.artis.artis.presentation.drawing.components.DrawingCanvas
import com.artis.artis.presentation.drawing.components.DrawingTopBar
import com.artis.artis.presentation.drawing.components.DrawingToolbarLeft
import com.artis.artis.presentation.drawing.components.DrawingToolbarRight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingScreen(
    viewModel: DrawingViewModel = hiltViewModel(), // Use hiltViewModel for actual app
    modifier: Modifier = Modifier
) {
    val strokes by viewModel.strokes
    val currentDrawingColor by viewModel.currentDrawingColor
    val currentStrokeWidth by viewModel.currentStrokeWidth
    val canvasBackgroundColor by viewModel.canvasBackgroundColor
    val currentToolType by viewModel.currentToolType // New: Observe current tool
    val currentStrokeAlpha by viewModel.currentStrokeAlpha // New: Observe current alpha

    var isLeftToolbarVisible by remember { mutableStateOf(true) }
    var isRightToolbarVisible by remember { mutableStateOf(true) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF1E1E2E), // Deeper, more modern dark background
        topBar = {
            DrawingTopBar(
                onMenuClick = { /* TODO */ },
                onGalleryClick = { /* TODO */ },
                onAddClick = { /* TODO */ },
                onUndoClick = { viewModel.undo() },
                onRedoClick = { viewModel.redo() },
                onSettingsClick = { /* TODO */ },
                title = "Untitled Artwork"
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Main Drawing Canvas
            DrawingCanvas(
                strokes = strokes,
                getCurrentDrawingColor = {
                    // If eraser is active, the color shown on the live path should be the canvas background
                    if (currentToolType == ToolType.ERASER) canvasBackgroundColor else currentDrawingColor
                },
                currentStrokeWidth = { viewModel.currentStrokeWidth.value },
                currentStrokeAlpha = { viewModel.currentStrokeAlpha.value }, // New
                canvasBackgroundColor = canvasBackgroundColor,
                onAddStroke = { path -> viewModel.addStroke(path) } // Modified
            )

            // Left Sidebar
            AnimatedVisibility(
                visible = isLeftToolbarVisible,
                enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                DrawingToolbarLeft(
                    currentDrawingColor = currentDrawingColor,
                    onBrushClick = { viewModel.setCurrentTool(ToolType.BRUSH) }, // Set brush tool
                    onSmudgeClick = { viewModel.setCurrentTool(ToolType.SMUDGE) }, // Set smudge tool
                    onEraserClick = { viewModel.setCurrentTool(ToolType.ERASER) }, // Set eraser tool
                    onLayersClick = { /* TODO */ },
                    onColorSelected = { newColor -> viewModel.setCurrentDrawingColor(newColor) },
                    modifier = Modifier.align(Alignment.CenterStart)
                        .pointerInput(Unit){
                            detectHorizontalDragGestures { _, dragAmount ->
                                // if user swipes left (dragAmount < threshold), hide
                                if (dragAmount < 4) isLeftToolbarVisible = false
                            }
                        },
                    currentToolType = currentToolType // Pass current tool to highlight active one
                )
            }

            if (!isLeftToolbarVisible) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
                        .align(Alignment.CenterStart)
                        .width(24.dp)
                        .fillMaxHeight(0.3f)
                        .background(Color.Black.copy(alpha = 0.2f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                // if user swipes right, show the toolbar
                                if (dragAmount > -10) isLeftToolbarVisible = true
                            }
                        }
                        .clickable { isLeftToolbarVisible = true }, // Make the whole box clickable
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "≫",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            // Right Sidebar
            AnimatedVisibility(
                visible = isRightToolbarVisible,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                DrawingToolbarRight(
                    currentStrokeWidth = currentStrokeWidth,
                    onStrokeWidthChange = { viewModel.setCurrentStrokeWidth(it) },
                    currentOpacity = currentStrokeAlpha,
                    onOpacityChange = { viewModel.setCurrentStrokeAlpha(it) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                // if user swipes right (dragAmount > threshold), hide
                                if (dragAmount > 4) isRightToolbarVisible = false
                            }
                        }
                )
            }
            // When hidden, show a transparent '≪' indicator at the edge
            if (!isRightToolbarVisible) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                        .align(Alignment.CenterEnd)
                        .width(24.dp)
                        .fillMaxHeight(0.3f)
                        .background(Color.Black.copy(alpha = 0.2f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                // if user swipes left, show the toolbar
                                if (dragAmount < -10) isRightToolbarVisible = true
                            }
                        }
                        .clickable { isRightToolbarVisible = true }, // Make the whole box clickable
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "≪",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }


            // Bottom Actions (Background Toggle, Clear Canvas)
            DrawingBottomActions(
                onToggleBackgroundClick = { viewModel.toggleCanvasBackground() },
                onClearCanvasClick = { viewModel.clearCanvas() }
            )
        }
    }
}
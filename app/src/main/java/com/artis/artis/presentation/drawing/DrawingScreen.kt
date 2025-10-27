package com.artis.artis.presentation.drawing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            DrawingToolbarLeft(
                currentDrawingColor = currentDrawingColor,
                onBrushClick = { viewModel.setCurrentTool(ToolType.BRUSH) }, // Set brush tool
                onSmudgeClick = { viewModel.setCurrentTool(ToolType.SMUDGE) }, // Set smudge tool
                onEraserClick = { viewModel.setCurrentTool(ToolType.ERASER) }, // Set eraser tool
                onLayersClick = { /* TODO */ },
                onColorSelected = { newColor -> viewModel.setCurrentDrawingColor(newColor) },
                modifier = Modifier.align(Alignment.CenterStart),
                currentToolType = currentToolType // Pass current tool to highlight active one
            )

            // Right Sidebar
            DrawingToolbarRight(
                currentStrokeWidth = currentStrokeWidth,
                onStrokeWidthChange = { viewModel.setCurrentStrokeWidth(it) },
                currentOpacity = currentStrokeAlpha, // Pass current alpha
                onOpacityChange = { viewModel.setCurrentStrokeAlpha(it) }, // Connect opacity change
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            // Bottom Actions (Background Toggle, Clear Canvas)
            DrawingBottomActions(
                onToggleBackgroundClick = { viewModel.toggleCanvasBackground() },
                onClearCanvasClick = { viewModel.clearCanvas() }
            )
        }
    }
}
package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.artis.artis.R
import com.artis.artis.presentation.drawing.ToolType // Import ToolType

@Composable
fun DrawingToolbarLeft(
    currentDrawingColor: Color,
    currentToolType: ToolType, // New: to highlight active tool
    onBrushClick: () -> Unit,
    onSmudgeClick: () -> Unit,
    onEraserClick: () -> Unit,
    onLayersClick: () -> Unit,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showColorPickerDialog by remember { mutableStateOf(false) }

    val activeToolColor = MaterialTheme.colorScheme.primary // Or any highlight color
    val inactiveToolColor = Color.White.copy(alpha = 0.7f)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF28283E).copy(alpha = 0.9f))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolIcon(
            Icons.Default.Brush,
            "Brush",
            tint = if (currentToolType == ToolType.BRUSH) activeToolColor else inactiveToolColor,
            onClick = onBrushClick
        )
        ToolIcon(
            Icons.Default.ContentCopy,
            "Smudge",
            tint = if (currentToolType == ToolType.SMUDGE) activeToolColor else inactiveToolColor,
            onClick = onSmudgeClick
        ) // Placeholder
        ToolIcon(
            painterResource(R.drawable.ic_eraser),
            "Eraser",
            tint = if (currentToolType == ToolType.ERASER) activeToolColor else inactiveToolColor,
            onClick = onEraserClick
        )

        Divider(modifier = Modifier.width(40.dp), color = Color.Gray.copy(alpha = 0.3f))

        ToolIcon(Icons.Default.Layers, "Layers", onClick = onLayersClick)

        // Color Picker Button & Popup
        Box {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(currentDrawingColor, shape = CircleShape)
                    .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                    .clickable { showColorPickerDialog = true }
            )

            if (showColorPickerDialog) {
                Popup(
                    alignment = Alignment.CenterStart,
                    offset = IntOffset(x = with(LocalDensity.current) { 60.dp.toPx().toInt() }, y = with(LocalDensity.current) { -150.dp.toPx().toInt() }),
                    onDismissRequest = { showColorPickerDialog = false }
                ) {
                    ColorPickerDialog(
                        initialColor = currentDrawingColor,
                        onColorSelected = { newColor ->
                            onColorSelected(newColor)
                            showColorPickerDialog = false
                        },
                        onDismiss = { showColorPickerDialog = false }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDrawingToolbarLeft() {
    MaterialTheme {
        DrawingToolbarLeft(
            currentDrawingColor = Color.Blue,
            currentToolType = ToolType.BRUSH, // Added for preview
            onBrushClick = {},
            onSmudgeClick = {},
            onEraserClick = {},
            onLayersClick = {},
            onColorSelected = {}
        )
    }
}
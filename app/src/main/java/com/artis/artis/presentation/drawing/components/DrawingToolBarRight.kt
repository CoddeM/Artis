package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawingToolbarRight(
    currentStrokeWidth: Float,
    onStrokeWidthChange: (Float) -> Unit,
    currentOpacity: Float, // Renamed from onOpacityChange to currentOpacity
    onOpacityChange: (Float) -> Unit, // Opacity is now implemented
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF28283E).copy(alpha = 0.9f))
            .width(64.dp)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Brush Size Slider (Vertical)
        Text("Size", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
        VerticalSlider(
            value = currentStrokeWidth,
            onValueChange = onStrokeWidthChange,
            valueRange = 2f..50f,
            modifier = Modifier
                .height(150.dp)
                .width(40.dp)
        )

        Divider(modifier = Modifier.width(40.dp), color = Color.Gray.copy(alpha = 0.3f))

        // Opacity Slider (Vertical)
        Text("Opacity", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
        VerticalSlider(
            value = currentOpacity, // Use currentOpacity
            onValueChange = onOpacityChange,
            valueRange = 0.01f..1f, // Min opacity slightly above 0 to remain visible
            modifier = Modifier
                .height(150.dp)
                .width(40.dp)
        )
    }
}


@Preview
@Composable
fun PreviewDrawingToolbarRight() {
    MaterialTheme {
        DrawingToolbarRight(
            currentStrokeWidth = 15f,
            onStrokeWidthChange = {},
            currentOpacity = 0.7f, // Added for preview
            onOpacityChange = {}
        )
    }
}
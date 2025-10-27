package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawingBottomActions(
    onToggleBackgroundClick: () -> Unit,
    onClearCanvasClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Canvas Background Toggle Button
        FloatingActionButton(
            onClick = onToggleBackgroundClick,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
            containerColor = Color(0xFF28283E),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(Icons.Default.Adjust, contentDescription = "Toggle Canvas Background")
        }

        // Clear Canvas Button
        FloatingActionButton(
            onClick = onClearCanvasClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF28283E),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Clear Canvas")
        }
    }
}

@Preview
@Composable
fun PreviewDrawingBottomActions() {
    MaterialTheme {
        Box(modifier = Modifier.size(300.dp, 200.dp).background(Color.LightGray)) { // Provide a background for context
            DrawingBottomActions(
                onToggleBackgroundClick = {},
                onClearCanvasClick = {}
            )
        }
    }
}
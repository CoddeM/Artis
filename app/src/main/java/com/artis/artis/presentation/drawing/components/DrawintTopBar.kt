package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawingTopBar(
    onMenuClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onAddClick: () -> Unit,
    onUndoClick: () -> Unit,
    onRedoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF28283E))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left side actions (Menu, Gallery, Add File)
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onGalleryClick) {
                Icon(Icons.Default.Share, contentDescription = "Share / Export", tint = Color.White)
            }
            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add New", tint = Color.White)
            }
        }

        // Center (Document Title)
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.8f),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Right side actions (Undo, Redo, Settings)
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onUndoClick) {
                Icon(Icons.Default.Undo, contentDescription = "Undo", tint = Color.White)
            }
            IconButton(onClick = onRedoClick) {
                Icon(Icons.Default.Redo, contentDescription = "Redo", tint = Color.White)
            }
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawingTopBar() {
    MaterialTheme {
        DrawingTopBar(
            onMenuClick = {},
            onGalleryClick = {},
            onAddClick = {},
            onUndoClick = {},
            onRedoClick = {},
            onSettingsClick = {},
            title = "My Awesome Art"
        )
    }
}
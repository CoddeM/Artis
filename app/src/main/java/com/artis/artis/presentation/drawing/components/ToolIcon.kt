package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ToolIcon(
    imageVector: ImageVector,
    contentDescription: String,
    isSelected: Boolean = false,
    tint: Color = Color.White,
    onClick: () -> Unit
) {
    val background = if (isSelected) Color(0x33FFFFFF) else Color.Transparent

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(background)
    ) {
        Icon(imageVector, contentDescription = contentDescription, tint = tint)
    }
}
// ✅ For Painter (custom icons from drawable)
@Composable
fun ToolIcon(
    painter: Painter,
    contentDescription: String,
    tint: Color = Color.White,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
    ) {
        Icon(painter = painter, contentDescription = contentDescription, tint = tint)
    }
}
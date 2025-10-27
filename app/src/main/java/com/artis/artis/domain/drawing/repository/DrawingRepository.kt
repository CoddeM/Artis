package com.artis.artis.domain.drawing.repository

import androidx.compose.ui.graphics.Color
import com.artis.artis.domain.drawing.model.Stroke
import kotlinx.coroutines.flow.Flow

interface DrawingRepository {
    fun getStrokes(): Flow<List<Stroke>>
    suspend fun addStroke(stroke: Stroke)
    suspend fun undoLastStroke()
    suspend fun redoLastStroke()
    suspend fun clearAllStrokes()

    fun getCurrentDrawingColor(): Flow<Color>
    suspend fun setCurrentDrawingColor(color: Color)

    fun getCurrentStrokeWidth(): Flow<Float>
    suspend fun setCurrentStrokeWidth(width: Float)

    fun getCurrentStrokeAlpha(): Flow<Float> // New
    suspend fun setCurrentStrokeAlpha(alpha: Float) // New

    fun getCanvasBackgroundColor(): Flow<Color>
    suspend fun setCanvasBackgroundColor(color: Color)
}
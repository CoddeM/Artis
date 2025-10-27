package com.artis.artis.data.drawing.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.artis.artis.domain.drawing.model.Stroke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

// In-memory data source for strokes
@Singleton
class DrawingLocalDataSource @Inject constructor() {

    private val _strokes = MutableStateFlow<List<Stroke>>(emptyList())
    val strokes: StateFlow<List<Stroke>> = _strokes.asStateFlow()

    private val undoneStrokes = mutableListOf<Stroke>()

    private val _currentDrawingColor = MutableStateFlow(Color.Black)
    val currentDrawingColor: StateFlow<Color> = _currentDrawingColor.asStateFlow()

    private val _currentStrokeWidth = MutableStateFlow(8f)
    val currentStrokeWidth: StateFlow<Float> = _currentStrokeWidth.asStateFlow()

    private val _currentStrokeAlpha = MutableStateFlow(1f) // New: default to full opacity
    val currentStrokeAlpha: StateFlow<Float> = _currentStrokeAlpha.asStateFlow()

    private val _canvasBackgroundColor = MutableStateFlow(Color.White)
    val canvasBackgroundColor: StateFlow<Color> = _canvasBackgroundColor.asStateFlow()


    fun getStrokes(): Flow<List<Stroke>> = strokes

    suspend fun addStroke(stroke: Stroke) {
        _strokes.update { it + stroke }
        undoneStrokes.clear() // Clear redo history when a new stroke is added
    }

    suspend fun undoLastStroke() {
        val lastStroke = _strokes.value.lastOrNull()
        if (lastStroke != null) {
            _strokes.update { it.dropLast(1) }
            undoneStrokes.add(lastStroke)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun redoLastStroke() {
        val nextStroke = undoneStrokes.lastOrNull()
        if (nextStroke != null) {
            undoneStrokes.removeLast()
            _strokes.update { it + nextStroke }
        }
    }

    suspend fun clearAllStrokes() {
        _strokes.update { emptyList() }
        undoneStrokes.clear()
    }

    suspend fun setCurrentDrawingColor(color: Color) {
        _currentDrawingColor.value = color
    }

    fun getCurrentDrawingColor(): Flow<Color> = currentDrawingColor

    suspend fun setCurrentStrokeWidth(width: Float) {
        _currentStrokeWidth.value = width
    }

    fun getCurrentStrokeWidth(): Flow<Float> = currentStrokeWidth

    suspend fun setCurrentStrokeAlpha(alpha: Float) { // New: Set current stroke alpha
        _currentStrokeAlpha.value = alpha
    }

    fun getCurrentStrokeAlpha(): Flow<Float> = currentStrokeAlpha // New: Get current stroke alpha

    suspend fun setCanvasBackgroundColor(color: Color) {
        _canvasBackgroundColor.value = color
    }

    fun getCanvasBackgroundColor(): Flow<Color> = canvasBackgroundColor
}
package com.artis.artis.presentation.drawing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artis.artis.domain.drawing.model.Stroke
import com.artis.artis.domain.drawing.usecase.* // Import all use cases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ToolType { // New enum for tool selection
    BRUSH,
    SMUDGE, // Keeping smudge as a placeholder for future implementation
    ERASER
}

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val addStrokeUseCase: AddStrokeUseCase,
    private val getStrokesUseCase: GetStrokesUseCase,
    private val undoUseCase: UndoUseCase,
    private val redoUseCase: RedoUseCase,
    private val clearStrokesUseCase: ClearStrokesUseCase,
// Inject other use cases for color, stroke width, background, and now opacity
    private val setCurrentDrawingColorUseCase: SetCurrentDrawingColorUseCase,
    private val getCurrentDrawingColorUseCase: GetCurrentDrawingColorUseCase,
    private val setCurrentStrokeWidthUseCase: SetCurrentStrokeWidthUseCase,
    private val getCurrentStrokeWidthUseCase: GetCurrentStrokeWidthUseCase,
    private val setCanvasBackgroundColorUseCase: SetCanvasBackgroundColorUseCase,
    private val getCanvasBackgroundColorUseCase: GetCanvasBackgroundColorUseCase,
    private val setCurrentStrokeAlphaUseCase: SetCurrentStrokeAlphaUseCase, // New
    private val getCurrentStrokeAlphaUseCase: GetCurrentStrokeAlphaUseCase // New
) : ViewModel() {
    private val _strokes = mutableStateOf<List<Stroke>>(emptyList())
    val strokes: State<List<Stroke>> = _strokes

    private val _currentDrawingColor = mutableStateOf(Color.Black)
    val currentDrawingColor: State<Color> = _currentDrawingColor

    private val _currentStrokeWidth = mutableFloatStateOf(8f)
    val currentStrokeWidth: State<Float> = _currentStrokeWidth

    private val _canvasBackgroundColor = mutableStateOf(Color.White)
    val canvasBackgroundColor: State<Color> = _canvasBackgroundColor

    private val _currentToolType = mutableStateOf(ToolType.BRUSH) // New: default tool is brush
    val currentToolType: State<ToolType> = _currentToolType

    private val _currentStrokeAlpha = mutableFloatStateOf(1f) // New: default alpha is 1f (full opacity)
    val currentStrokeAlpha: State<Float> = _currentStrokeAlpha


    init {
        getStrokes()
        getCurrentDrawingSettings()
    }

    private fun getStrokes() {
        getStrokesUseCase().onEach { currentStrokes ->
            _strokes.value = currentStrokes
        }.launchIn(viewModelScope)
    }

    private fun getCurrentDrawingSettings() {
        getCurrentDrawingColorUseCase().onEach { color ->
            _currentDrawingColor.value = color
        }.launchIn(viewModelScope)

        getCurrentStrokeWidthUseCase().onEach { width ->
            _currentStrokeWidth.value = width
        }.launchIn(viewModelScope)

        getCanvasBackgroundColorUseCase().onEach { color ->
            _canvasBackgroundColor.value = color
        }.launchIn(viewModelScope)

        getCurrentStrokeAlphaUseCase().onEach { alpha -> // New
            _currentStrokeAlpha.value = alpha
        }.launchIn(viewModelScope)
    }

    fun addStroke(path: Path) { // Modified to take a Path directly
        viewModelScope.launch {
            val strokeColor = if (_currentToolType.value == ToolType.ERASER) {
                _canvasBackgroundColor.value // Eraser "paints" with canvas background color
            } else {
                _currentDrawingColor.value
            }
            val strokeAlpha = _currentStrokeAlpha.value
            val isEraser = _currentToolType.value == ToolType.ERASER

            println("DEBUG: ViewModel creating stroke with color: $strokeColor, alpha: $strokeAlpha, isEraser: $isEraser")
            addStrokeUseCase(
                Stroke(
                    path = path,
                    color = strokeColor,
                    strokeWidth = _currentStrokeWidth.value,
                    alpha = strokeAlpha,
                    isEraser = isEraser
                )
            )
        }
    }

    fun undo() {
        viewModelScope.launch {
            undoUseCase()
        }
    }

    fun redo() {
        viewModelScope.launch {
            redoUseCase()
        }
    }

    fun clearCanvas() {
        viewModelScope.launch {
            clearStrokesUseCase()
        }
    }

    fun setCurrentDrawingColor(color: Color) {
        viewModelScope.launch {
            setCurrentDrawingColorUseCase(color)
            // When color is selected, ensure tool is BRUSH
            _currentToolType.value = ToolType.BRUSH
        }
    }

    fun setCurrentStrokeWidth(width: Float) {
        viewModelScope.launch {
            setCurrentStrokeWidthUseCase(width)
        }
    }

    fun toggleCanvasBackground() {
        viewModelScope.launch {
            val currentColor = _canvasBackgroundColor.value
            val newColor = if (currentColor == Color.White) Color.Black else Color.White
            setCanvasBackgroundColorUseCase(newColor)
        }
    }

    fun setCurrentTool(toolType: ToolType) { // New: Function to set current tool
        _currentToolType.value = toolType
    }

    fun setCurrentStrokeAlpha(alpha: Float) { // New: Function to set current stroke alpha
        viewModelScope.launch {
            setCurrentStrokeAlphaUseCase(alpha)
        }
    }
}
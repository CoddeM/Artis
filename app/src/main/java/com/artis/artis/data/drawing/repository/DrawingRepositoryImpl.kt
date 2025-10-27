package com.artis.artis.data.drawing

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.artis.artis.data.drawing.local.DrawingLocalDataSource
import com.artis.artis.domain.drawing.model.Stroke
import com.artis.artis.domain.drawing.repository.DrawingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawingRepositoryImpl @Inject constructor(
    private val localDataSource: DrawingLocalDataSource
) : DrawingRepository {

    override fun getStrokes(): Flow<List<Stroke>> = localDataSource.getStrokes()

    override suspend fun addStroke(stroke: Stroke) {
        localDataSource.addStroke(stroke)
    }

    override suspend fun undoLastStroke() {
        localDataSource.undoLastStroke()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override suspend fun redoLastStroke() {
        localDataSource.redoLastStroke()
    }

    override suspend fun clearAllStrokes() {
        localDataSource.clearAllStrokes()
    }

    override fun getCurrentDrawingColor(): Flow<Color> = localDataSource.getCurrentDrawingColor()

    override suspend fun setCurrentDrawingColor(color: Color) {
        localDataSource.setCurrentDrawingColor(color)
    }

    override fun getCurrentStrokeWidth(): Flow<Float> = localDataSource.getCurrentStrokeWidth()

    override suspend fun setCurrentStrokeWidth(width: Float) {
        localDataSource.setCurrentStrokeWidth(width)
    }

    override fun getCurrentStrokeAlpha(): Flow<Float> = localDataSource.getCurrentStrokeAlpha() // New

    override suspend fun setCurrentStrokeAlpha(alpha: Float) { // New
        localDataSource.setCurrentStrokeAlpha(alpha)
    }

    override fun getCanvasBackgroundColor(): Flow<Color> = localDataSource.getCanvasBackgroundColor()

    override suspend fun setCanvasBackgroundColor(color: Color) {
        localDataSource.setCanvasBackgroundColor(color)
    }
}
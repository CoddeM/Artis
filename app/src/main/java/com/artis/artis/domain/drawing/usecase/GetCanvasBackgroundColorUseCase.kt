package com.artis.artis.domain.drawing.usecase

import androidx.compose.ui.graphics.Color
import com.artis.artis.domain.drawing.repository.DrawingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCanvasBackgroundColorUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    operator fun invoke(): Flow<Color> {
        return repository.getCanvasBackgroundColor()
    }
}
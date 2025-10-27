package com.artis.artis.domain.drawing.usecase

import androidx.compose.ui.graphics.Color
import com.artis.artis.domain.drawing.repository.DrawingRepository
import javax.inject.Inject

class SetCanvasBackgroundColorUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    suspend operator fun invoke(color: Color) {
        repository.setCanvasBackgroundColor(color)
    }
}
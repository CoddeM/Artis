package com.artis.artis.domain.drawing.usecase

import androidx.compose.ui.graphics.Color
import com.artis.artis.domain.drawing.repository.DrawingRepository
import javax.inject.Inject

class SetCurrentDrawingColorUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    suspend operator fun invoke(color: Color) {
        repository.setCurrentDrawingColor(color)
    }
}
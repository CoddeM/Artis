package com.artis.artis.domain.drawing.usecase

import com.artis.artis.domain.drawing.repository.DrawingRepository
import javax.inject.Inject

class SetCurrentStrokeAlphaUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    suspend operator fun invoke(alpha: Float) {
        repository.setCurrentStrokeAlpha(alpha)
    }
}